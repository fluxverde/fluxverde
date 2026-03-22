package io.fluxverde.domain.company.audit;

import io.fluxverde.domain.audit.EAuditFindingCategory;
import io.fluxverde.domain.audit.EAuditFindingFeasibility;
import io.fluxverde.domain.audit.EFundingAvailable;
import io.fluxverde.domain.audit.EImplementationTimeline;
import io.fluxverde.domain.company.Site;
import io.fluxverde.domain.company.meter.Meter;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

public record AuditFinding(
    Long id,
    String findingCode,
    String findingTitle,
    EAuditFindingCategory category,
    String description,
    String currentStatus,
    String recommendedAction,
    String technicalParameters,
    BigDecimal estimatedAnnualEnergySavingsTJ,
    BigDecimal estimatedAnnualEnergySavingskWh,
    BigDecimal estimatedAnnualCostSavingsEUR,
    BigDecimal estimatedImplementationCostEUR,
    Double paybackPeriodYears,
    Double estimatedROIPercent,
    EAuditFindingPriority priority,
    EAuditFindingFeasibility feasibility,
    EImplementationTimeline implementationTimeline,
    EFundingAvailable fundingAvailable,
    String fundingSource,
    EAuditFindingStatus status,
    String evidencePhotoPath,
    String referenceMaterial,
    Instant createdAt,
    Instant updatedAt,
    Integer affectedMeterCount,
    EnergyAudit audit,
    Meter primaryMeter,
    Site site,
    Set<Meter> affectedMeters
) {
}
