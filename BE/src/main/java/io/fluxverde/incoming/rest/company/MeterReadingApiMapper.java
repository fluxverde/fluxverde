package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.meter.MeterReading;
import io.fluxverde.rest.model.MeterReadingModel;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MeterReadingApiMapper {
    MeterReading toDomain(MeterReadingModel api);

    MeterReadingModel toApi(MeterReading domain);
}

