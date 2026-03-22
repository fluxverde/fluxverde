package io.fluxverde.incoming.rest.audit;

import io.fluxverde.domain.company.audit.AuditChecklist;
import io.fluxverde.rest.model.AuditChecklistApi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditChecklistApiMapper {
    AuditChecklist toDomain(AuditChecklistApi api);

    AuditChecklistApi toApi(AuditChecklist domain);
}

