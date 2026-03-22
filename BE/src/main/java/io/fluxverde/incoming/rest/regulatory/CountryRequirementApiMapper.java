package io.fluxverde.incoming.rest.regulatory;

import io.fluxverde.domain.regulatory.CountryRequirement;
import io.fluxverde.rest.model.CountryRequirementApi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryRequirementApiMapper {
    CountryRequirement toDomain(CountryRequirementApi api);

    CountryRequirementApi toApi(CountryRequirement domain);
}

