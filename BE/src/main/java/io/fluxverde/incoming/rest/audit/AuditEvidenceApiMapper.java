package io.fluxverde.incoming.rest.audit;

import io.fluxverde.domain.company.audit.AuditEvidence;
import io.fluxverde.rest.model.AuditEvidenceModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditEvidenceApiMapper {
    AuditEvidence toDomain(AuditEvidenceModel api);

    AuditEvidenceModel toApi(AuditEvidence domain);
}

