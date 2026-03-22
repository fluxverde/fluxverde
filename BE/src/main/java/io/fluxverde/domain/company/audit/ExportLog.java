package io.fluxverde.domain.company.audit;

import io.fluxverde.domain.report.EExportLogEntity;
import io.fluxverde.domain.report.EExportPurpose;
import io.fluxverde.domain.report.EExportType;
import java.time.Instant;

public record ExportLog(
    Long id,
    String exportCode,
    EExportType exportType,
    String exportFormat,
    Instant exportedAt,
    String exportedBy,
    EExportLogEntity exportedEntity,
    Long recordsExported,
    String exportFileName,
    String exportFilePath,
    String recipientEmail,
    EExportPurpose purpose,
    Instant createdAt,
    Instant updatedAt,
    EnergyAudit audit
) {
}
