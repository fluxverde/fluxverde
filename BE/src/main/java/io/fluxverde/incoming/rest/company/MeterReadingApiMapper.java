package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.meter.MeterReading;
import io.fluxverde.rest.model.MeterReadingApi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeterReadingApiMapper {
    MeterReading toDomain(MeterReadingApi api);

    MeterReadingApi toApi(MeterReading domain);
}

