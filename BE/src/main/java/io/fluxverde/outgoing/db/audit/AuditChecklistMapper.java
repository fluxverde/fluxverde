package io.fluxverde.outgoing.db.audit;

import io.fluxverde.domain.company.audit.AuditChecklist;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditChecklistMapper {
    AuditChecklist toDomain(AuditChecklistEntity entity);

    AuditChecklistEntity toEntity(AuditChecklist domain);
}
