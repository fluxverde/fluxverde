package io.fluxverde.incoming.rest.audit;

import io.fluxverde.domain.company.audit.AuditEvidence;
import io.fluxverde.rest.model.AuditEvidenceApi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditEvidenceApiMapper {
    AuditEvidence toDomain(AuditEvidenceApi api);

    AuditEvidenceApi toApi(AuditEvidence domain);
}

