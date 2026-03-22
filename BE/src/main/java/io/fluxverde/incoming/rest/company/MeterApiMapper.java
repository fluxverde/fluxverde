package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.meter.Meter;
import io.fluxverde.rest.model.MeterModel;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MeterApiMapper {
    Meter toDomain(MeterModel api);

    MeterModel toApi(Meter domain);
}

