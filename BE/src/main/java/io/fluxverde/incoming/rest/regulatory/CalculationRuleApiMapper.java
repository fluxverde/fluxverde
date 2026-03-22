package io.fluxverde.incoming.rest.regulatory;

import io.fluxverde.domain.regulatory.CalculationRule;
import io.fluxverde.rest.model.CalculationRuleModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CalculationRuleApiMapper {
    CalculationRule toDomain(CalculationRuleModel api);

    CalculationRuleModel toApi(CalculationRule domain);
}

