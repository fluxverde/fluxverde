package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.meter.Meter;
import io.fluxverde.rest.model.MeterApi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeterApiMapper {
    Meter toDomain(MeterApi api);

    MeterApi toApi(Meter domain);
}

