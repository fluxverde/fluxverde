package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.Company;
import io.fluxverde.domain.company.CompanyUser;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import io.fluxverde.rest.model.CompanyModel;
import io.fluxverde.rest.model.CompanyUserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyUserApiMapper {
    CompanyUser toDomain(CompanyUserModel api);

    CompanyUserModel toApi(CompanyUser domain);

    default Company toCompany(CompanyModel value) {
        if (value == null) {
            return null;
        }
        return new Company(value.getId(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    default CompanyModel toCompanyModel(Company value) {
        if (value == null) {
            return null;
        }
        CompanyModel model = new CompanyModel();
        model.setId(value.id());
        return model;
    }
}
