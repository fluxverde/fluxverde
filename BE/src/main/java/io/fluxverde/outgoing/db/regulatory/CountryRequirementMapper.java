package io.fluxverde.outgoing.db.regulatory;

import io.fluxverde.domain.regulatory.CountryRequirement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryRequirementMapper {
    CountryRequirement toDomain(CountryRequirementEntity entity);

    CountryRequirementEntity toEntity(CountryRequirement domain);
}
