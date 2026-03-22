package io.fluxverde.outgoing.db.visualization;

import io.fluxverde.domain.visualization.ConsumptionTrend;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConsumptionTrendMapper {
    ConsumptionTrend toDomain(ConsumptionTrendEntity entity);

    ConsumptionTrendEntity toEntity(ConsumptionTrend domain);
}
