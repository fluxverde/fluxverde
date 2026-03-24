package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.CompanyUser;
import io.fluxverde.domain.company.UserAuditAccess;
import io.fluxverde.domain.company.audit.EnergyAudit;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import io.fluxverde.rest.model.CompanyUserModel;
import io.fluxverde.rest.model.EnergyAuditModel;
import io.fluxverde.rest.model.UserAuditAccessModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserAuditAccessApiMapper {
    UserAuditAccess toDomain(UserAuditAccessModel api);

    UserAuditAccessModel toApi(UserAuditAccess domain);

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
