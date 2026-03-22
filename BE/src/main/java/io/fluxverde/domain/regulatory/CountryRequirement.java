package io.fluxverde.domain.regulatory;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record CountryRequirement(
    Long id,
    String requirementCode,
    String requirementTitle,
    String country,
    ERegulatorySource regulatorySource,
    String sourceReference,
    String description,
    String applicableSiteType,
    BigDecimal energyThresholdTJ,
    String implementationGuidance,
    LocalDate deadline,
    String referenceDocumentURL,
    String contactAuthority,
    Instant createdAt,
    Instant updatedAt,
    RegulatoryTemplate template
) {
}
