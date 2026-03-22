package io.fluxverde.incoming.rest.audit;

import io.fluxverde.domain.company.audit.AuditFinding;
import io.fluxverde.rest.model.AuditFindingModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditFindingApiMapper {
    AuditFinding toDomain(AuditFindingModel api);

    AuditFindingModel toApi(AuditFinding domain);
}

