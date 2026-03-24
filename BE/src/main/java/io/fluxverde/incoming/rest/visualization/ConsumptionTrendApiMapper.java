package io.fluxverde.incoming.rest.visualization;

import io.fluxverde.domain.company.Site;
import io.fluxverde.domain.company.meter.Meter;
import io.fluxverde.domain.visualization.ConsumptionTrend;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import io.fluxverde.rest.model.ConsumptionTrendModel;
import io.fluxverde.rest.model.MeterModel;
import io.fluxverde.rest.model.SiteModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConsumptionTrendApiMapper {
    ConsumptionTrend toDomain(ConsumptionTrendModel api);

    ConsumptionTrendModel toApi(ConsumptionTrend domain);

    default Site toSite(SiteModel value) {
        if (value == null) {
            return null;
        }
        return new Site(value.getId(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    default SiteModel toSiteModel(Site value) {
        if (value == null) {
            return null;
        }
        SiteModel model = new SiteModel();
        model.setId(value.id());
        return model;
    }

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
}
