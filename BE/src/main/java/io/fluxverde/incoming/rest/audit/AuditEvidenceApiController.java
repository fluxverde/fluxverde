package io.fluxverde.incoming.rest.audit;

import io.fluxverde.domain.company.audit.AuditEvidenceService;
import io.fluxverde.rest.api.AuditEvidenceApi;
import io.fluxverde.rest.model.AuditEvidenceModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuditEvidenceApiController implements AuditEvidenceApi {
    private final AuditEvidenceService service;
    private final AuditEvidenceApiMapper mapper;

    @Override
    public ResponseEntity<AuditEvidenceModel> createAuditEvidence(AuditEvidenceModel auditEvidenceApi) {
        AuditEvidenceModel created = mapper.toApi(service.create(mapper.toDomain(auditEvidenceApi)));
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteAuditEvidence(Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<AuditEvidenceModel> getAuditEvidenceById(Long id) {
        return service.findById(id).map(mapper::toApi).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<AuditEvidenceModel>> listAuditEvidences() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toApi).toList());
    }

    @Override
    public ResponseEntity<AuditEvidenceModel> updateAuditEvidence(Long id, AuditEvidenceModel auditEvidenceApi) {
        return service.update(id, mapper.toDomain(auditEvidenceApi))
            .map(mapper::toApi)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
