package io.fluxverde.outgoing.db.company;

import io.fluxverde.domain.company.meter.MeterReading;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeterReadingMapper {
    MeterReading toDomain(MeterReadingEntity entity);

    MeterReadingEntity toEntity(MeterReading domain);
}
