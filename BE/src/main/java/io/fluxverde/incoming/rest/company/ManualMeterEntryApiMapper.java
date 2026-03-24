package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.CompanyUser;
import io.fluxverde.domain.company.meter.ManualMeterEntry;
import io.fluxverde.domain.company.meter.Meter;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import io.fluxverde.rest.model.CompanyUserModel;
import io.fluxverde.rest.model.ManualMeterEntryModel;
import io.fluxverde.rest.model.MeterModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ManualMeterEntryApiMapper {
    ManualMeterEntry toDomain(ManualMeterEntryModel api);

    ManualMeterEntryModel toApi(ManualMeterEntry domain);

    default Meter toMeter(MeterModel value) {
        if (value == null) {
            return null;
        }
        return new Meter(value.getId(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    default MeterModel toMeterModel(Meter value) {
        if (value == null) {
            return null;
        }
        MeterModel model = new MeterModel();
        model.setId(value.id());
        return model;
    }

    default CompanyUser toCompanyUser(CompanyUserModel value) {
        if (value == null) {
            return null;
        }
        return new CompanyUser(value.getId(), null, null, null, null, null, null, null, null, null, null);
    }

    default CompanyUserModel toCompanyUserModel(CompanyUser value) {
        if (value == null) {
            return null;
        }
        CompanyUserModel model = new CompanyUserModel();
        model.setId(value.id());
        return model;
    }
}
