package io.fluxverde.incoming.rest.audit;

import io.fluxverde.domain.company.audit.AuditFindingService;
import io.fluxverde.rest.api.AuditFindingApi;
import io.fluxverde.rest.model.AuditFindingModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuditFindingApiController implements AuditFindingApi {
    private final AuditFindingService service;
    private final AuditFindingApiMapper mapper;

    @Override
    public ResponseEntity<AuditFindingModel> createAuditFinding(AuditFindingModel auditFindingApi) {
        AuditFindingModel created = mapper.toApi(service.create(mapper.toDomain(auditFindingApi)));
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteAuditFinding(Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<AuditFindingModel> getAuditFindingById(Long id) {
        return service.findById(id).map(mapper::toApi).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<AuditFindingModel>> listAuditFindings() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toApi).toList());
    }

    @Override
    public ResponseEntity<AuditFindingModel> updateAuditFinding(Long id, AuditFindingModel auditFindingApi) {
        return service.update(id, mapper.toDomain(auditFindingApi))
            .map(mapper::toApi)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
