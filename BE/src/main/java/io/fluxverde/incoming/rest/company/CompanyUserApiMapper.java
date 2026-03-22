package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.CompanyUser;
import io.fluxverde.rest.model.CompanyUserApi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyUserApiMapper {
    CompanyUser toDomain(CompanyUserApi api);

    CompanyUserApi toApi(CompanyUser domain);
}

