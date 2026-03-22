package io.fluxverde.incoming.rest.audit;

import io.fluxverde.domain.company.audit.AuditChecklist;
import io.fluxverde.rest.model.AuditChecklistModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditChecklistApiMapper {
    AuditChecklist toDomain(AuditChecklistModel api);

    AuditChecklistModel toApi(AuditChecklist domain);
}

