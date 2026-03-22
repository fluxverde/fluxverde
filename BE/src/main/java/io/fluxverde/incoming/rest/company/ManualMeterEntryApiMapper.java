package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.meter.ManualMeterEntry;
import io.fluxverde.rest.model.ManualMeterEntryModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ManualMeterEntryApiMapper {
    ManualMeterEntry toDomain(ManualMeterEntryModel api);

    ManualMeterEntryModel toApi(ManualMeterEntry domain);
}

