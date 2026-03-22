package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.UserAuditAccess;
import io.fluxverde.rest.model.UserAuditAccessApi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAuditAccessApiMapper {
    UserAuditAccess toDomain(UserAuditAccessApi api);

    UserAuditAccessApi toApi(UserAuditAccess domain);
}

