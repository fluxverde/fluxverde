package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.UserAuditAccess;
import io.fluxverde.rest.model.UserAuditAccessModel;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserAuditAccessApiMapper {
    UserAuditAccess toDomain(UserAuditAccessModel api);

    UserAuditAccessModel toApi(UserAuditAccess domain);
}

