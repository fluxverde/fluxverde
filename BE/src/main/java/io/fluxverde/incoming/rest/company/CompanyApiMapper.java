package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.Company;
import io.fluxverde.rest.model.CompanyModel;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyApiMapper {
    Company toDomain(CompanyModel api);

    CompanyModel toApi(Company domain);
}

