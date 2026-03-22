package io.fluxverde.outgoing.db.company;

import io.fluxverde.domain.company.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    Company toDomain(CompanyEntity entity);

    CompanyEntity toEntity(Company domain);
}
