package io.fluxverde.incoming.rest.regulatory;

import io.fluxverde.domain.regulatory.CalculationRule;
import io.fluxverde.rest.model.CalculationRuleApi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CalculationRuleApiMapper {
    CalculationRule toDomain(CalculationRuleApi api);

    CalculationRuleApi toApi(CalculationRule domain);
}

