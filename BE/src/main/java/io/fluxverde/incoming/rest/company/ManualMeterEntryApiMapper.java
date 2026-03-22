package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.meter.ManualMeterEntry;
import io.fluxverde.rest.model.ManualMeterEntryApi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ManualMeterEntryApiMapper {
    ManualMeterEntry toDomain(ManualMeterEntryApi api);

    ManualMeterEntryApi toApi(ManualMeterEntry domain);
}

