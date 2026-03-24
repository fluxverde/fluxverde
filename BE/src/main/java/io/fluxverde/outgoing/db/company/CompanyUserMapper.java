package io.fluxverde.outgoing.db.company;

import io.fluxverde.domain.company.CompanyUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CompanyMapper.class)
public interface CompanyUserMapper {
    @Mapping(target = "company", source = "company", qualifiedByName = "toDomainShallow")
    CompanyUser toDomain(CompanyUserEntity entity);

    @Mapping(target = "company", source = "company", qualifiedByName = "toEntityShallow")
    CompanyUserEntity toEntity(CompanyUser domain);
}
