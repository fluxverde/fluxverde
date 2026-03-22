package io.fluxverde.outgoing.db.audit;

import io.fluxverde.domain.company.audit.AuditEvidence;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditEvidenceMapper {
    AuditEvidence toDomain(AuditEvidenceEntity entity);

    AuditEvidenceEntity toEntity(AuditEvidence domain);
}
