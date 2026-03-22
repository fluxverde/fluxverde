package io.fluxverde.incoming.rest.regulatory;

import io.fluxverde.domain.regulatory.CalculationRule;
import io.fluxverde.rest.model.CalculationRuleModel;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CalculationRuleApiMapper {
    CalculationRule toDomain(CalculationRuleModel api);

    CalculationRuleModel toApi(CalculationRule domain);
}

