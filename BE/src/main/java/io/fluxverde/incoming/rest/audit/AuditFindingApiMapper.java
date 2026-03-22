package io.fluxverde.incoming.rest.audit;

import io.fluxverde.domain.company.audit.AuditFinding;
import io.fluxverde.rest.model.AuditFindingApi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditFindingApiMapper {
    AuditFinding toDomain(AuditFindingApi api);

    AuditFindingApi toApi(AuditFinding domain);
}

