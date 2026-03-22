package io.fluxverde.domain.company.audit;

import io.fluxverde.domain.company.Site;
import io.fluxverde.domain.regulatory.ERegulatoryRequirement;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record EnergyAudit(
    Long id,
    String auditCode,
    LocalDate auditStartDate,
    LocalDate auditEndDate,
    EAuditType auditType,
    String leadAuditor,
    String supportingAuditors,
    LocalDate referenceStartDate,
    LocalDate referenceEndDate,
    EAuditState auditStatus,
    ERegulatoryRequirement regulatoryRequirement,
    String complianceFramework,
    BigDecimal totalEnergyConsumptionTJ,
    BigDecimal totalEnergyConsumptionkWh,
    BigDecimal estimatedAnnualSavingsTJ,
    BigDecimal estimatedCostSavingsEUR,
    BigDecimal estimatedROIPercent,
    Boolean reportGenerated,
    Instant reportGeneratedDate,
    String reportPath,
    Instant createdAt,
    Instant updatedAt,
    Site site
) {
}
