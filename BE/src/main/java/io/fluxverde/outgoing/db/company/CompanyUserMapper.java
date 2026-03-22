package io.fluxverde.outgoing.db.company;

import io.fluxverde.domain.company.CompanyUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyUserMapper {
    CompanyUser toDomain(CompanyUserEntity entity);

    CompanyUserEntity toEntity(CompanyUser domain);
}
