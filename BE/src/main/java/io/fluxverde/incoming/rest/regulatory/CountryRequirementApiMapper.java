package io.fluxverde.incoming.rest.regulatory;

import io.fluxverde.domain.regulatory.CountryRequirement;
import io.fluxverde.rest.model.CountryRequirementModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryRequirementApiMapper {
    CountryRequirement toDomain(CountryRequirementModel api);

    CountryRequirementModel toApi(CountryRequirement domain);
}

