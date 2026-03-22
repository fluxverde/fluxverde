package io.fluxverde.domain.visualization;

import io.fluxverde.domain.company.Company;
import java.math.BigDecimal;
import java.time.Instant;

public record EnergyBenchmark(
    Long id,
    String benchmarkCode,
    String country,
    String industry,
    String siteType,
    BigDecimal typicalEnergyUsekWhM2,
    BigDecimal medianEnergyUsekWhM2,
    BigDecimal bestInClasskWhM2,
    Integer sourceDataYear,
    Integer numberOfFacilitiesInSample,
    String description,
    String sourceReference,
    Instant createdAt,
    Instant updatedAt,
    Company company
) {
}
