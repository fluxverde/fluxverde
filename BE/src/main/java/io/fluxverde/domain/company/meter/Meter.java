package io.fluxverde.domain.company.meter;

import io.fluxverde.domain.company.Site;
import java.time.Instant;
import java.time.LocalDate;

public record Meter(
    Long id,
    String meterName,
    String meterCode,
    String meterSerialNumber,
    String manufacturer,
    LocalDate installationDate,
    Integer nominalPower,
    String accuracy,
    String location,
    EMeterCategory meterCategory,
    EMeterCollectionMethod collectionMethod,
    EMeterReadingFrequency readingFrequency,
    Boolean isActive,
    Instant createdAt,
    Instant updatedAt,
    Site site,
    MeterType meterType
) {
}
