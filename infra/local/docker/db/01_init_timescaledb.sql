-- ==============================================================================
-- FLUXVERDE — Database Initialisation Script
-- Runs automatically on first container startup
-- ==============================================================================
-- This script:
--   1. Enables the TimescaleDB extension
--   2. Creates the meter_reading hypertable (time-series optimised)
--   3. Configures compression and continuous aggregates
--   4. Creates useful indexes
-- ==============================================================================
-- NOTE: JHipster/Flyway will create the full schema (all entities).
--       This script only handles TimescaleDB-specific setup that Flyway
--       cannot do automatically.
-- ==============================================================================

\echo '==> Enabling TimescaleDB extension...'
CREATE EXTENSION IF NOT EXISTS timescaledb CASCADE;

\echo '==> TimescaleDB version:'
SELECT extversion FROM pg_extension WHERE extname = 'timescaledb';

-- ==============================================================================
-- HELPER FUNCTION: Convert hypertable after Flyway creates the table
-- Call this after your first Flyway migration runs
-- ==============================================================================

-- This function is called by the Flyway migration that creates meter_reading
-- You can also call it manually after first run:
--   SELECT setup_fluxverde_timescaledb();

CREATE OR REPLACE FUNCTION setup_fluxverde_timescaledb()
RETURNS void AS $$
BEGIN

    -- ── HYPERTABLE: meter_reading ───────────────────────────────────────────
    -- Convert meter_reading to a TimescaleDB hypertable
    -- Partitioned by reading_timestamp in 1-week chunks
    -- (1 week = good balance for compression vs query performance)

    IF EXISTS (
        SELECT 1 FROM information_schema.tables
        WHERE table_name = 'meter_reading'
    ) THEN
        PERFORM create_hypertable(
            'meter_reading',
            'reading_timestamp',
            chunk_time_interval => INTERVAL '1 week',
            if_not_exists       => TRUE
        );

        RAISE NOTICE 'meter_reading hypertable created';

        -- ── COMPRESSION ────────────────────────────────────────────────────
        -- Compress chunks older than 7 days
        -- segment by meter_id: readings from the same meter compress best together
        -- order by reading_timestamp: sequential access pattern
        ALTER TABLE meter_reading SET (
            timescaledb.compress,
            timescaledb.compress_segmentby = 'meter_id',
            timescaledb.compress_orderby   = 'reading_timestamp DESC'
        );

        SELECT add_compression_policy(
            'meter_reading',
            INTERVAL '7 days',
            if_not_exists => TRUE
        );

        RAISE NOTICE 'meter_reading compression policy added (7 days)';

    ELSE
        RAISE NOTICE 'meter_reading table does not exist yet — run after Flyway migrations';
    END IF;

    -- ── HYPERTABLE: consumption_trend ──────────────────────────────────────
    IF EXISTS (
        SELECT 1 FROM information_schema.tables
        WHERE table_name = 'consumption_trend'
    ) THEN
        PERFORM create_hypertable(
            'consumption_trend',
            'trend_date',
            chunk_time_interval => INTERVAL '1 month',
            if_not_exists       => TRUE
        );

        ALTER TABLE consumption_trend SET (
            timescaledb.compress,
            timescaledb.compress_segmentby = 'site_id',
            timescaledb.compress_orderby   = 'trend_date DESC'
        );

        SELECT add_compression_policy(
            'consumption_trend',
            INTERVAL '30 days',
            if_not_exists => TRUE
        );

        RAISE NOTICE 'consumption_trend hypertable created';
    END IF;

END;
$$ LANGUAGE plpgsql;

-- ==============================================================================
-- CONTINUOUS AGGREGATES
-- Pre-computed rollups for dashboard queries
-- Created after hypertables exist
-- ==============================================================================

CREATE OR REPLACE FUNCTION setup_continuous_aggregates()
RETURNS void AS $$
BEGIN

    IF NOT EXISTS (
        SELECT 1 FROM timescaledb_information.continuous_aggregates
        WHERE view_name = 'meter_reading_hourly'
    ) THEN

        -- ── HOURLY AGGREGATE ────────────────────────────────────────────────
        EXECUTE $AGG$
            CREATE MATERIALIZED VIEW meter_reading_hourly
            WITH (timescaledb.continuous) AS
            SELECT
                time_bucket('1 hour', reading_timestamp) AS bucket,
                meter_id,
                AVG(reading_value)  AS avg_value,
                MAX(reading_value)  AS max_value,
                MIN(reading_value)  AS min_value,
                SUM(reading_value)  AS total_value,
                COUNT(*)            AS reading_count
            FROM meter_reading
            WHERE reading_status = 'VALID'
              AND is_outlier      = FALSE
            GROUP BY bucket, meter_id
            WITH NO DATA
        $AGG$;

        PERFORM add_continuous_aggregate_policy(
            'meter_reading_hourly',
            start_offset      => INTERVAL '3 hours',
            end_offset        => INTERVAL '1 hour',
            schedule_interval => INTERVAL '1 hour',
            if_not_exists     => TRUE
        );

        RAISE NOTICE 'meter_reading_hourly continuous aggregate created';
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM timescaledb_information.continuous_aggregates
        WHERE view_name = 'meter_reading_daily'
    ) THEN

        -- ── DAILY AGGREGATE ─────────────────────────────────────────────────
        EXECUTE $AGG$
            CREATE MATERIALIZED VIEW meter_reading_daily
            WITH (timescaledb.continuous) AS
            SELECT
                time_bucket('1 day', bucket) AS bucket,
                meter_id,
                AVG(avg_value)   AS avg_value,
                MAX(max_value)   AS max_value,
                MIN(min_value)   AS min_value,
                SUM(total_value) AS total_value,
                SUM(reading_count) AS reading_count
            FROM meter_reading_hourly
            GROUP BY time_bucket('1 day', bucket), meter_id
            WITH NO DATA
        $AGG$;

        PERFORM add_continuous_aggregate_policy(
            'meter_reading_daily',
            start_offset      => INTERVAL '2 days',
            end_offset        => INTERVAL '1 day',
            schedule_interval => INTERVAL '1 day',
            if_not_exists     => TRUE
        );

        RAISE NOTICE 'meter_reading_daily continuous aggregate created';
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM timescaledb_information.continuous_aggregates
        WHERE view_name = 'meter_reading_monthly'
    ) THEN

        -- ── MONTHLY AGGREGATE ───────────────────────────────────────────────
        EXECUTE $AGG$
            CREATE MATERIALIZED VIEW meter_reading_monthly
            WITH (timescaledb.continuous) AS
            SELECT
                time_bucket('1 month', bucket) AS bucket,
                meter_id,
                AVG(avg_value)   AS avg_value,
                MAX(max_value)   AS max_value,
                MIN(min_value)   AS min_value,
                SUM(total_value) AS total_value,
                SUM(reading_count) AS reading_count
            FROM meter_reading_daily
            GROUP BY time_bucket('1 month', bucket), meter_id
            WITH NO DATA
        $AGG$;

        PERFORM add_continuous_aggregate_policy(
            'meter_reading_monthly',
            start_offset      => INTERVAL '2 months',
            end_offset        => INTERVAL '1 month',
            schedule_interval => INTERVAL '1 day',
            if_not_exists     => TRUE
        );

        RAISE NOTICE 'meter_reading_monthly continuous aggregate created';
    END IF;

END;
$$ LANGUAGE plpgsql;

-- ==============================================================================
-- DATA RETENTION POLICY
-- Automatically drop raw readings older than 10 years
-- (EN 16247 requires keeping audit data for at least the duration between audits)
-- ==============================================================================

CREATE OR REPLACE FUNCTION setup_retention_policy()
RETURNS void AS $$
BEGIN
    -- Keep raw meter readings for 10 years
    -- Monthly aggregates are kept indefinitely (they're tiny)
    IF EXISTS (
        SELECT 1 FROM information_schema.tables
        WHERE table_name = 'meter_reading'
    ) THEN
        PERFORM add_retention_policy(
            'meter_reading',
            INTERVAL '10 years',
            if_not_exists => TRUE
        );
        RAISE NOTICE 'Data retention policy: raw readings kept for 10 years';
    END IF;
END;
$$ LANGUAGE plpgsql;

-- ==============================================================================
-- USEFUL INDEXES
-- Created after Flyway runs — call setup_indexes() manually or via Flyway
-- ==============================================================================

CREATE OR REPLACE FUNCTION setup_indexes()
RETURNS void AS $$
BEGIN

    -- meter_reading: primary access patterns
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'meter_reading') THEN

        -- Range query by meter + time (most common)
        IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_mr_meter_timestamp') THEN
            CREATE INDEX idx_mr_meter_timestamp
                ON meter_reading (meter_id, reading_timestamp DESC)
                WHERE reading_status = 'VALID' AND is_outlier = FALSE;
            RAISE NOTICE 'Index idx_mr_meter_timestamp created';
        END IF;

        -- Range query by status (for data quality reports)
        IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_mr_status') THEN
            CREATE INDEX idx_mr_status
                ON meter_reading (reading_status, reading_timestamp DESC);
            RAISE NOTICE 'Index idx_mr_status created';
        END IF;

    END IF;

    -- site: lookup by company
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'site') THEN
        IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_site_company') THEN
            CREATE INDEX idx_site_company ON site (company_id);
            RAISE NOTICE 'Index idx_site_company created';
        END IF;
    END IF;

    -- meter: lookup by site
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'meter') THEN
        IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_meter_site') THEN
            CREATE INDEX idx_meter_site ON meter (site_id) WHERE is_active = TRUE;
            RAISE NOTICE 'Index idx_meter_site created';
        END IF;
    END IF;

    -- energy_audit: lookup by site + status
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'energy_audit') THEN
        IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_audit_site_status') THEN
            CREATE INDEX idx_audit_site_status ON energy_audit (site_id, audit_status);
            RAISE NOTICE 'Index idx_audit_site_status created';
        END IF;
    END IF;

END;
$$ LANGUAGE plpgsql;

-- ==============================================================================
-- HANDY MONITORING VIEWS
-- Useful for checking database health and TimescaleDB status
-- ==============================================================================

-- View: chunk information for hypertables
CREATE OR REPLACE VIEW v_chunk_info AS
SELECT
    hypertable_name,
    chunk_name,
    range_start,
    range_end,
    pg_size_pretty(before_compression_total_bytes) AS size_before,
    pg_size_pretty(after_compression_total_bytes)  AS size_after,
    CASE
        WHEN before_compression_total_bytes > 0
        THEN ROUND(100 - (after_compression_total_bytes::numeric /
             before_compression_total_bytes * 100), 1)
        ELSE NULL
    END AS compression_ratio_pct
FROM timescaledb_information.chunks
ORDER BY hypertable_name, range_start DESC;

-- View: overall database size summary
CREATE OR REPLACE VIEW v_db_size_summary AS
SELECT
    schemaname,
    tablename,
    pg_size_pretty(pg_total_relation_size(schemaname||'.'||tablename)) AS total_size,
    pg_size_pretty(pg_relation_size(schemaname||'.'||tablename))       AS table_size,
    pg_size_pretty(pg_indexes_size(schemaname||'.'||tablename))        AS index_size
FROM pg_tables
WHERE schemaname = 'public'
ORDER BY pg_total_relation_size(schemaname||'.'||tablename) DESC;

-- View: meter reading statistics per meter
CREATE OR REPLACE VIEW v_meter_reading_stats AS
SELECT
    m.id          AS meter_id,
    m.meter_name,
    m.meter_code,
    s.site_name,
    COUNT(mr.id)                        AS total_readings,
    MIN(mr.reading_timestamp)           AS first_reading,
    MAX(mr.reading_timestamp)           AS last_reading,
    ROUND(AVG(mr.reading_value)::numeric, 2) AS avg_value,
    COUNT(*) FILTER (WHERE mr.reading_status = 'VALID')   AS valid_count,
    COUNT(*) FILTER (WHERE mr.reading_status = 'ANOMALY') AS anomaly_count,
    COUNT(*) FILTER (WHERE mr.is_outlier = TRUE)          AS outlier_count
FROM meter_reading mr
JOIN meter  m ON mr.meter_id  = m.id
JOIN site   s ON m.site_id    = s.id
GROUP BY m.id, m.meter_name, m.meter_code, s.site_name;

\echo ''
\echo '==> Fluxverde database initialised successfully'
\echo '==> TimescaleDB extension enabled'
\echo '==> Helper functions created:'
\echo '      SELECT setup_fluxverde_timescaledb();  -- Run after Flyway migrations'
\echo '      SELECT setup_continuous_aggregates();   -- Run after hypertables exist'
\echo '      SELECT setup_retention_policy();        -- Run after hypertables exist'
\echo '      SELECT setup_indexes();                 -- Run after Flyway migrations'
\echo ''
