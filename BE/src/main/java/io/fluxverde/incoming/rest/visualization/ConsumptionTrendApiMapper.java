package io.fluxverde.incoming.rest.visualization;

import io.fluxverde.domain.visualization.ConsumptionTrend;
import io.fluxverde.rest.model.ConsumptionTrendModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConsumptionTrendApiMapper {
    ConsumptionTrend toDomain(ConsumptionTrendModel api);

    ConsumptionTrendModel toApi(ConsumptionTrend domain);
}

