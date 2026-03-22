package io.fluxverde.domain.visualization;

import io.fluxverde.domain.company.Site;
import io.fluxverde.domain.company.meter.Meter;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record ConsumptionTrend(
    Long id,
    LocalDate trendDate,
    EAggregationPeriod aggregationPeriod,
    BigDecimal energyConsumptionkWh,
    BigDecimal energyConsumptionTJ,
    BigDecimal specificEnergyUsekWhM2,
    BigDecimal costPerUnit,
    BigDecimal comparedToPreviousPeriod,
    EConsumptionTrendDirection trend,
    BigDecimal ambientTemperatureC,
    Boolean isClimateAdjusted,
    Instant createdAt,
    Instant updatedAt,
    Site site,
    Meter meter
) {
}
