package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.meter.MeterType;
import io.fluxverde.rest.model.MeterTypeApi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeterTypeApiMapper {
    MeterType toDomain(MeterTypeApi api);

    MeterTypeApi toApi(MeterType domain);
}

