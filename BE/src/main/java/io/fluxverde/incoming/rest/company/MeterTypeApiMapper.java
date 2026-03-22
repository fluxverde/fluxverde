package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.meter.MeterType;
import io.fluxverde.rest.model.MeterTypeModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeterTypeApiMapper {
    MeterType toDomain(MeterTypeModel api);

    MeterTypeModel toApi(MeterType domain);
}

