package io.fluxverde.outgoing.db.audit;

import io.fluxverde.domain.visualization.EnergyBenchmark;
import io.fluxverde.outgoing.db.company.CompanyMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CompanyMapper.class)
public interface EnergyBenchmarkMapper {
    @Mapping(target = "company", source = "company", qualifiedByName = "toDomainShallow")
    EnergyBenchmark toDomain(EnergyBenchmarkEntity entity);

    @Mapping(target = "company", source = "company", qualifiedByName = "toEntityShallow")
    EnergyBenchmarkEntity toEntity(EnergyBenchmark domain);
}
