package io.fluxverde.incoming.rest.regulatory;

import io.fluxverde.domain.regulatory.RegulatoryTemplate;
import io.fluxverde.rest.model.RegulatoryTemplateModel;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegulatoryTemplateApiMapper {
    RegulatoryTemplate toDomain(RegulatoryTemplateModel api);

    RegulatoryTemplateModel toApi(RegulatoryTemplate domain);
}

