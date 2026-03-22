package io.fluxverde.incoming.rest.audit;

import io.fluxverde.domain.company.audit.EnergyAuditService;
import io.fluxverde.rest.api.EnergyAuditApi;
import io.fluxverde.rest.model.EnergyAuditModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EnergyAuditApiController implements EnergyAuditApi {
    private final EnergyAuditService service;
    private final EnergyAuditApiMapper mapper;

    @Override
    public ResponseEntity<EnergyAuditModel> createEnergyAudit(EnergyAuditModel energyAuditApi) {
        EnergyAuditModel created = mapper.toApi(service.create(mapper.toDomain(energyAuditApi)));
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteEnergyAudit(Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<EnergyAuditModel> getEnergyAuditById(Long id) {
        return service.findById(id).map(mapper::toApi).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<EnergyAuditModel>> listEnergyAudits() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toApi).toList());
    }

    @Override
    public ResponseEntity<EnergyAuditModel> updateEnergyAudit(Long id, EnergyAuditModel energyAuditApi) {
        return service.update(id, mapper.toDomain(energyAuditApi))
            .map(mapper::toApi)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
