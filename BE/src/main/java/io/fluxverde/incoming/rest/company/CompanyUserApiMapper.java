package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.CompanyUser;
import io.fluxverde.rest.model.CompanyUserModel;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyUserApiMapper {
    CompanyUser toDomain(CompanyUserModel api);

    CompanyUserModel toApi(CompanyUser domain);
}

