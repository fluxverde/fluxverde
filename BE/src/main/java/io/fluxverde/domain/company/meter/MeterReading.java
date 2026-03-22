package io.fluxverde.domain.company.meter;

import io.fluxverde.domain.company.CSVUpload;
import java.math.BigDecimal;
import java.time.Instant;

public record MeterReading(
    Long id,
    Instant readingTimestamp,
    BigDecimal readingValue,
    EMeterReadingState readingStatus,
    Integer confidence,
    EMeterReadingSource source,
    String enteredByUser,
    Instant enteredAt,
    Boolean isOutlier,
    String anomalyReason,
    String notes,
    Long batchId,
    BigDecimal normalizedValue,
    Meter meter,
    CSVUpload sourceUpload
) {
}
