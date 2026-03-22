package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.Company;
import io.fluxverde.rest.model.CompanyApi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyApiMapper {
    Company toDomain(CompanyApi api);

    CompanyApi toApi(Company domain);
}

