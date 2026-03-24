package io.fluxverde.incoming.rest.audit;

import io.fluxverde.domain.company.Site;
import io.fluxverde.domain.company.audit.EnergyAudit;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import io.fluxverde.rest.model.EnergyAuditModel;
import io.fluxverde.rest.model.SiteModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EnergyAuditApiMapper {
    EnergyAudit toDomain(EnergyAuditModel api);

    EnergyAuditModel toApi(EnergyAudit domain);

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
}
