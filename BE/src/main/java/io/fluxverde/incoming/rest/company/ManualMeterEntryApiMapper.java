package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.meter.ManualMeterEntry;
import io.fluxverde.rest.model.ManualMeterEntryModel;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ManualMeterEntryApiMapper {
    ManualMeterEntry toDomain(ManualMeterEntryModel api);

    ManualMeterEntryModel toApi(ManualMeterEntry domain);
}

