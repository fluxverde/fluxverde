package io.fluxverde.incoming.rest.visualization;

import io.fluxverde.domain.visualization.EnergyBenchmark;
import io.fluxverde.rest.model.EnergyBenchmarkModel;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EnergyBenchmarkApiMapper {
    EnergyBenchmark toDomain(EnergyBenchmarkModel api);

    EnergyBenchmarkModel toApi(EnergyBenchmark domain);
}

