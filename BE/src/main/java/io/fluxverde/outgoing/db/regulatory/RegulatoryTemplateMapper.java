package io.fluxverde.outgoing.db.regulatory;

import io.fluxverde.domain.regulatory.RegulatoryTemplate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegulatoryTemplateMapper {
    RegulatoryTemplate toDomain(RegulatoryTemplateEntity entity);

    RegulatoryTemplateEntity toEntity(RegulatoryTemplate domain);
}
