package io.fluxverde.incoming.rest.visualization;

import io.fluxverde.domain.visualization.EnergyBenchmark;
import io.fluxverde.rest.model.EnergyBenchmarkApi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnergyBenchmarkApiMapper {
    EnergyBenchmark toDomain(EnergyBenchmarkApi api);

    EnergyBenchmarkApi toApi(EnergyBenchmark domain);
}

