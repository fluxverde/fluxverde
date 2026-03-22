package io.fluxverde.domain.regulatory;

import java.math.BigDecimal;
import java.time.Instant;

public record CalculationRule(
    Long id,
    String ruleCode,
    String ruleName,
    String country,
    ECalculationSiteType applicableToSiteType,
    String applicableToIndustry,
    String formulaDescription,
    ECalculationNormalizationFactor normalizationFactor,
    BigDecimal benchmarkValue,
    String climateAdjustmentFactors,
    BigDecimal productionVolumeFactor,
    String calculationFormula,
    Instant createdAt,
    Instant updatedAt,
    RegulatoryTemplate template
) {
}
