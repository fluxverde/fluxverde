package io.fluxverde.incoming.rest.visualization;

import io.fluxverde.domain.visualization.EnergyBenchmark;
import io.fluxverde.rest.model.EnergyBenchmarkModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnergyBenchmarkApiMapper {
    EnergyBenchmark toDomain(EnergyBenchmarkModel api);

    EnergyBenchmarkModel toApi(EnergyBenchmark domain);
}

