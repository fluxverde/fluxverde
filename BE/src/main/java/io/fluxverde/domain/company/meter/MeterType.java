package io.fluxverde.domain.company.meter;

import java.math.BigDecimal;

public record MeterType(
    Long id,
    String typeName,
    EMeterTypeUnit unit,
    EMeterTypeCategory category,
    BigDecimal conversionFactorToTJ,
    String description
) {
}

