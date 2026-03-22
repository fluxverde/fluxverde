package io.fluxverde.domain.company.meter;

import io.fluxverde.domain.company.CompanyUser;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record ManualMeterEntry(
    Long id,
    LocalDate entryDate,
    BigDecimal meterReadingValue,
    String unit,
    String enteredBy,
    Instant entryTimestamp,
    EVerificationState verificationStatus,
    String verifiedBy,
    Instant verificationTimestamp,
    String notes,
    String correctionNotes,
    String sourceDocument,
    Meter meter,
    CompanyUser enteredByUser,
    CompanyUser verifiedByUser
) {
}
