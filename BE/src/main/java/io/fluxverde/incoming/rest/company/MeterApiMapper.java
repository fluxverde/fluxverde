package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.meter.Meter;
import io.fluxverde.rest.model.MeterModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeterApiMapper {
    Meter toDomain(MeterModel api);

    MeterModel toApi(Meter domain);
}

