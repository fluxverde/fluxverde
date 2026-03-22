package io.fluxverde.outgoing.db.audit;

import io.fluxverde.domain.visualization.EnergyBenchmark;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnergyBenchmarkMapper {
    EnergyBenchmark toDomain(EnergyBenchmarkEntity entity);

    EnergyBenchmarkEntity toEntity(EnergyBenchmark domain);
}
