# 1. Copy and configure environment
cp .env.example .env
# Edit .env — set a real DB_PASSWORD

# 2. Start the database only
sudo docker-compose up -d db

sudo docker-compose restart db

# 3. Check it's healthy
sudo docker-compose ps
sudo docker-compose logs -f db


sudo docker-compose down -v
sudo docker-compose up -d

# 4. Connect via psql
docker-compose exec db psql -U fluxverde -d fluxverde

# 5. Start pgAdmin (dev only)
docker-compose --profile dev up -d pgadmin
# Then open: http://localhost:5050


# 6. create pg admin
sudo docker-compose -f infra/local/docker-compose.yml up -d pgadmin



docker exec -it fluxverde-db psql -U postgres -c "CREATE DATABASE fluxverde OWNER fluxverde;"



SELECT setup_fluxverde_timescaledb();  -- creates hypertables + compression
SELECT setup_continuous_aggregates();  -- hourly/daily/monthly rollups
SELECT setup_retention_policy();       -- 10-year raw data retention
SELECT setup_indexes();                -- performance indexes

SELECT tablename FROM pg_tables WHERE schemaname = 'public';

