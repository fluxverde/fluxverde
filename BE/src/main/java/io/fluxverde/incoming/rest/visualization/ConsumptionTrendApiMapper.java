package io.fluxverde.incoming.rest.visualization;

import io.fluxverde.domain.visualization.ConsumptionTrend;
import io.fluxverde.rest.model.ConsumptionTrendModel;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConsumptionTrendApiMapper {
    ConsumptionTrend toDomain(ConsumptionTrendModel api);

    ConsumptionTrendModel toApi(ConsumptionTrend domain);
}

