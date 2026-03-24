package io.fluxverde.incoming.rest.audit;

import io.fluxverde.domain.company.Site;
import io.fluxverde.domain.company.audit.AuditFinding;
import io.fluxverde.domain.company.audit.EnergyAudit;
import io.fluxverde.domain.company.meter.Meter;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import io.fluxverde.rest.model.AuditFindingModel;
import io.fluxverde.rest.model.EnergyAuditModel;
import io.fluxverde.rest.model.MeterModel;
import io.fluxverde.rest.model.SiteModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuditFindingApiMapper {
    AuditFinding toDomain(AuditFindingModel api);

    AuditFindingModel toApi(AuditFinding domain);

    default EnergyAudit toEnergyAudit(EnergyAuditModel value) {
        if (value == null) {
            return null;
        }
        return new EnergyAudit(
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
            null,
            null,
            null,
            null
        );
    }

    default EnergyAuditModel toEnergyAuditModel(EnergyAudit value) {
        if (value == null) {
            return null;
        }
        EnergyAuditModel model = new EnergyAuditModel();
        model.setId(value.id());
        return model;
    }

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

    default Meter toMeter(MeterModel value) {
        if (value == null) {
            return null;
        }
        return new Meter(
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
            null
        );
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
