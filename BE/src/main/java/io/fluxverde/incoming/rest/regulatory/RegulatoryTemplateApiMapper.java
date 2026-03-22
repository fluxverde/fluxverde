package io.fluxverde.incoming.rest.regulatory;

import io.fluxverde.domain.regulatory.RegulatoryTemplate;
import io.fluxverde.rest.model.RegulatoryTemplateApi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegulatoryTemplateApiMapper {
    RegulatoryTemplate toDomain(RegulatoryTemplateApi api);

    RegulatoryTemplateApi toApi(RegulatoryTemplate domain);
}

