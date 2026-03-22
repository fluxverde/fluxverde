package io.fluxverde.outgoing.db.audit;

import io.fluxverde.domain.company.audit.AuditFinding;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditFindingMapper {
    AuditFinding toDomain(AuditFindingEntity entity);

    AuditFindingEntity toEntity(AuditFinding domain);
}
