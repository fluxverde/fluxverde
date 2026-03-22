package io.fluxverde.domain.regulatory;

import io.fluxverde.domain.report.EReportFormatStandard;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record RegulatoryTemplate(
    Long id,
    String templateCode,
    String templateName,
    String country,
    ERegulatoryTemplateType regulatoryFramework,
    LocalDate validFromDate,
    LocalDate validUntilDate,
    EReportFormatStandard reportFormat,
    String requiredSections,
    String calculationMethodology,
    BigDecimal mandatorySEUThreshold,
    Boolean mandatoryCO2Reporting,
    Boolean mandatoryCostAnalysis,
    Integer submissionDeadlineMonths,
    ESubmissionFormatType submissionFormat,
    String submissionAuthority,
    String submissionURL,
    Integer recordRetentionYears,
    String reportLanguage,
    String version,
    String description,
    Instant createdAt,
    Instant updatedAt,
    ERegulatoryTemplateStatus status
) {
}
