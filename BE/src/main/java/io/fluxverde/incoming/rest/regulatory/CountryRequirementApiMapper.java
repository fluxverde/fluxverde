package io.fluxverde.incoming.rest.regulatory;

import io.fluxverde.domain.regulatory.CountryRequirement;
import io.fluxverde.rest.model.CountryRequirementModel;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CountryRequirementApiMapper {
    CountryRequirement toDomain(CountryRequirementModel api);

    CountryRequirementModel toApi(CountryRequirement domain);
}

