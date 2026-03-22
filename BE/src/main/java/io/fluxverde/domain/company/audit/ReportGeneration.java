package io.fluxverde.domain.company.audit;

import io.fluxverde.domain.report.EConfidentialityLevel;
import io.fluxverde.domain.report.EReportFormat;
import io.fluxverde.domain.report.EReportState;
import io.fluxverde.domain.report.EReportType;
import java.time.Instant;

public record ReportGeneration(
    Long id,
    String reportCode,
    EReportType reportType,
    EReportFormat reportFormat,
    Instant generatedAt,
    String generatedBy,
    String reportTitle,
    String reportFileName,
    String reportFilePath,
    Long reportFileSize,
    String regulatoryTemplateUsed,
    String country,
    Boolean includeFindings,
    Boolean includeRecommendations,
    Boolean includeCostAnalysis,
    Boolean includeCharts,
    EConfidentialityLevel confidentialityLevel,
    EReportState reportStatus,
    Instant submittedAt,
    String submittedTo,
    Integer version,
    String previousVersionPath,
    String notes,
    Instant createdAt,
    Instant updatedAt,
    EnergyAudit audit
) {
}
