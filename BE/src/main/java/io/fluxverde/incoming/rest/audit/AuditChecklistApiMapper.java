package io.fluxverde.incoming.rest.audit;

import io.fluxverde.domain.company.audit.AuditChecklist;
import io.fluxverde.domain.company.audit.EnergyAudit;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import io.fluxverde.rest.model.AuditChecklistModel;
import io.fluxverde.rest.model.EnergyAuditModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuditChecklistApiMapper {
    AuditChecklist toDomain(AuditChecklistModel api);

    AuditChecklistModel toApi(AuditChecklist domain);

    default EnergyAudit toEnergyAudit(EnergyAuditModel value) {
        if (value == null) {
            return null;
        }
        return new EnergyAudit(value.getId(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    default EnergyAuditModel toEnergyAuditModel(EnergyAudit value) {
        if (value == null) {
            return null;
        }
        EnergyAuditModel model = new EnergyAuditModel();
        model.setId(value.id());
        return model;
    }
}
