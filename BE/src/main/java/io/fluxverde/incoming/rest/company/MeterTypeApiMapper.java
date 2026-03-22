package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.meter.MeterType;
import io.fluxverde.rest.model.MeterTypeModel;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MeterTypeApiMapper {
    MeterType toDomain(MeterTypeModel api);

    MeterTypeModel toApi(MeterType domain);
}

