package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.Company;
import io.fluxverde.rest.model.CompanyModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyApiMapper {
    Company toDomain(CompanyModel api);

    CompanyModel toApi(Company domain);
}

