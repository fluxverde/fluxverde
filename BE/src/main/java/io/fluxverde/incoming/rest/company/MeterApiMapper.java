package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.Site;
import io.fluxverde.domain.company.meter.Meter;
import io.fluxverde.domain.company.meter.MeterType;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import io.fluxverde.rest.model.MeterModel;
import io.fluxverde.rest.model.MeterTypeModel;
import io.fluxverde.rest.model.SiteModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MeterApiMapper {
    Meter toDomain(MeterModel api);

    MeterModel toApi(Meter domain);

    default Site toSite(SiteModel value) {
        if (value == null) {
            return null;
        }
        return new Site(
            value.getId(),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        );
    }

    default SiteModel toSiteModel(Site value) {
        if (value == null) {
            return null;
        }
        SiteModel model = new SiteModel();
        model.setId(value.id());
        return model;
    }

    default MeterType toMeterType(MeterTypeModel value) {
        if (value == null) {
            return null;
        }
        return new MeterType(value.getId(), null, null, null, null, null);
    }

    default MeterTypeModel toMeterTypeModel(MeterType value) {
        if (value == null) {
            return null;
        }
        MeterTypeModel model = new MeterTypeModel();
        model.setId(value.id());
        return model;
    }
}
