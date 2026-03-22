package io.fluxverde.incoming.rest.audit;

import io.fluxverde.domain.company.audit.AuditChecklistService;
import io.fluxverde.rest.api.AuditChecklistApi;
import io.fluxverde.rest.model.AuditChecklistModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuditChecklistApiController implements AuditChecklistApi {
    private final AuditChecklistService service;
    private final AuditChecklistApiMapper mapper;

    @Override
    public ResponseEntity<AuditChecklistModel> createAuditChecklist(AuditChecklistModel auditChecklistApi) {
        AuditChecklistModel created = mapper.toApi(service.create(mapper.toDomain(auditChecklistApi)));
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteAuditChecklist(Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<AuditChecklistModel> getAuditChecklistById(Long id) {
        return service.findById(id).map(mapper::toApi).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<AuditChecklistModel>> listAuditChecklists() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toApi).toList());
    }

    @Override
    public ResponseEntity<AuditChecklistModel> updateAuditChecklist(Long id, AuditChecklistModel auditChecklistApi) {
        return service.update(id, mapper.toDomain(auditChecklistApi))
            .map(mapper::toApi)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
