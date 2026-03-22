package io.fluxverde.outgoing.db.company;

import io.fluxverde.domain.company.UserAuditAccess;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAuditAccessMapper {
    UserAuditAccess toDomain(UserAuditAccessEntity entity);

    UserAuditAccessEntity toEntity(UserAuditAccess domain);
}
