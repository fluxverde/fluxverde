package io.fluxverde.domain.company;

import io.fluxverde.domain.company.audit.EAuditMethodology;
import io.fluxverde.domain.regulatory.ERegulatoryObligation;
import io.fluxverde.domain.visualization.EnergyBenchmark;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public record Company(
    Long id,
    String companyName,
    String registrationNumber,
    String country,
    String legalRepresentative,
    String contactEmail,
    String contactPhone,
    String industry,
    Integer employeeCount,
    BigDecimal annualRevenueMeur,
    BigDecimal totalEnergyTjPerYear,
    ERegulatoryObligation regulatoryObligation,
    LocalDate nextMandatoryAuditDate,
    LocalDate lastAuditDate,
    EAuditMethodology auditMethodology,
    Instant createdAt,
    Instant updatedAt,
    ECompanyStatus status,
    List<Site> sites,
    List<CompanyUser> users,
    List<EnergyBenchmark> benchmarks
) {
}

