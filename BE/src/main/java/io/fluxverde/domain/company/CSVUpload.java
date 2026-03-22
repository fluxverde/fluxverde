package io.fluxverde.domain.company;

import io.fluxverde.domain.company.meter.MeterType;
import java.time.Instant;

public record CSVUpload(
    Long id,
    String fileName,
    Long fileSize,
    String uploadedBy,
    Instant uploadTimestamp,
    Long totalRecordsImported,
    Long successfulRecords,
    Long failedRecords,
    Long skippedRecords,
    EUploadStatus importStatus,
    String errorLog,
    Boolean isProcessed,
    Instant processedAt,
    String notes,
    Site site,
    MeterType meterType,
    Company company
) {
}
