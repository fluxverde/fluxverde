package io.fluxverde.outgoing.db.regulatory;

import io.fluxverde.domain.regulatory.CalculationRule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CalculationRuleMapper {
    CalculationRule toDomain(CalculationRuleEntity entity);

    CalculationRuleEntity toEntity(CalculationRule domain);
}
