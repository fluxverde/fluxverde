package io.fluxverde.outgoing.db.company;

import io.fluxverde.domain.company.meter.ManualMeterEntry;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {MeterMapper.class, CompanyUserMapper.class})
public interface ManualMeterEntryMapper {
    ManualMeterEntry toDomain(ManualMeterEntryEntity entity);

    ManualMeterEntryEntity toEntity(ManualMeterEntry domain);
}
