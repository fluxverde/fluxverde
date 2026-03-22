package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.meter.MeterReading;
import io.fluxverde.rest.model.MeterReadingModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeterReadingApiMapper {
    MeterReading toDomain(MeterReadingModel api);

    MeterReadingModel toApi(MeterReading domain);
}

