package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.UserAuditAccess;
import io.fluxverde.rest.model.UserAuditAccessModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAuditAccessApiMapper {
    UserAuditAccess toDomain(UserAuditAccessModel api);

    UserAuditAccessModel toApi(UserAuditAccess domain);
}

