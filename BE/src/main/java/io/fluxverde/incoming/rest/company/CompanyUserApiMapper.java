package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.CompanyUser;
import io.fluxverde.rest.model.CompanyUserModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyUserApiMapper {
    CompanyUser toDomain(CompanyUserModel api);

    CompanyUserModel toApi(CompanyUser domain);
}

