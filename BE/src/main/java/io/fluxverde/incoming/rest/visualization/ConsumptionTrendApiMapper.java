package io.fluxverde.incoming.rest.visualization;

import io.fluxverde.domain.visualization.ConsumptionTrend;
import io.fluxverde.rest.model.ConsumptionTrendApi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConsumptionTrendApiMapper {
    ConsumptionTrend toDomain(ConsumptionTrendApi api);

    ConsumptionTrendApi toApi(ConsumptionTrend domain);
}

