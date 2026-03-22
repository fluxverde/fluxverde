package io.fluxverde.domain.company;

import java.time.Instant;

public record CompanyUser(
    Long id,
    String userEmail,
    String firstName,
    String lastName,
    EUserRoles userRole,
    String phoneNumber,
    Boolean isActive,
    Instant lastLoginAt,
    Instant createdAt,
    Instant updatedAt,
    Company company
) {
}
