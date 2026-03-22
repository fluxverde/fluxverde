package io.fluxverde.outgoing.db.company;

import io.fluxverde.domain.company.meter.Meter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeterMapper {
    Meter toDomain(MeterEntity entity);

    MeterEntity toEntity(Meter domain);
}
