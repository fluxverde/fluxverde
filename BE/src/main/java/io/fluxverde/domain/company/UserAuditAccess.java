package io.fluxverde.domain.company;

import io.fluxverde.domain.audit.EAccessLevel;
import io.fluxverde.domain.company.audit.EnergyAudit;
import java.time.Instant;

public record UserAuditAccess(
    Long id,
    EAccessLevel accessLevel,
    Instant grantedAt,
    String grantedBy,
    Instant expiresAt,
    CompanyUser user,
    EnergyAudit audit
) {
}
