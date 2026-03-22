package io.fluxverde.outgoing.db.company;

import io.fluxverde.domain.company.meter.MeterType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeterTypeMapper {
    MeterType toDomain(MeterTypeEntity entity);

    MeterTypeEntity toEntity(MeterType domain);
}
