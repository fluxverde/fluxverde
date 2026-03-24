package io.fluxverde.incoming.rest.audit;

import io.fluxverde.domain.company.audit.AuditEvidence;
import io.fluxverde.domain.company.audit.AuditFinding;
import io.fluxverde.domain.company.audit.EnergyAudit;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import io.fluxverde.rest.model.AuditEvidenceModel;
import io.fluxverde.rest.model.AuditFindingModel;
import io.fluxverde.rest.model.EnergyAuditModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuditEvidenceApiMapper {
    AuditEvidence toDomain(AuditEvidenceModel api);

    AuditEvidenceModel toApi(AuditEvidence domain);

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

    default AuditFinding toAuditFinding(AuditFindingModel value) {
        if (value == null) {
            return null;
        }
        return new AuditFinding(value.getId(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    default AuditFindingModel toAuditFindingModel(AuditFinding value) {
        if (value == null) {
            return null;
        }
        AuditFindingModel model = new AuditFindingModel();
        model.setId(value.id());
        return model;
    }
}
