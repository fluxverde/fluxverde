package io.fluxverde.incoming.rest.regulatory;

import io.fluxverde.domain.regulatory.CalculationRule;
import io.fluxverde.domain.regulatory.RegulatoryTemplate;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import io.fluxverde.rest.model.CalculationRuleModel;
import io.fluxverde.rest.model.RegulatoryTemplateModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CalculationRuleApiMapper {
    CalculationRule toDomain(CalculationRuleModel api);

    CalculationRuleModel toApi(CalculationRule domain);

    default RegulatoryTemplate toRegulatoryTemplate(RegulatoryTemplateModel value) {
        if (value == null) {
            return null;
        }
        return new RegulatoryTemplate(value.getId(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    default RegulatoryTemplateModel toRegulatoryTemplateModel(RegulatoryTemplate value) {
        if (value == null) {
            return null;
        }
        RegulatoryTemplateModel model = new RegulatoryTemplateModel();
        model.setId(value.id());
        return model;
    }
}
