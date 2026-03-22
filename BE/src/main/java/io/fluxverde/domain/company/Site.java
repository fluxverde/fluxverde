package io.fluxverde.domain.company;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record Site(
    Long id,
    String siteName,
    String siteCode,
    String address,
    String city,
    String postalCode,
    String country,
    BigDecimal latitude,
    BigDecimal longitude,
    ESiteType siteType,
    String productionProcess,
    Integer totalAreaM2,
    BigDecimal estimatedAnnualConsumptionTJ,
    BigDecimal estimatedAnnualConsumptionkWh,
    LocalDate lastAuditDate,
    LocalDate nextAuditDate,
    ESiteAuditStatus auditStatus,
    Instant createdAt,
    Instant updatedAt,
    ESiteStatus status
) {
}

