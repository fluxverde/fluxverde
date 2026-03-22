package io.fluxverde.incoming.rest.regulatory;

import io.fluxverde.domain.regulatory.RegulatoryTemplate;
import io.fluxverde.rest.model.RegulatoryTemplateModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegulatoryTemplateApiMapper {
    RegulatoryTemplate toDomain(RegulatoryTemplateModel api);

    RegulatoryTemplateModel toApi(RegulatoryTemplate domain);
}

