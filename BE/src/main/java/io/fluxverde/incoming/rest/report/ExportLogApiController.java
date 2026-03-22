package io.fluxverde.incoming.rest.report;

import io.fluxverde.domain.company.audit.ExportLogService;
import io.fluxverde.rest.api.ExportLogApi;
import io.fluxverde.rest.model.ExportLogModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExportLogApiController implements ExportLogApi {
    private final ExportLogService service;
    private final ExportLogApiMapper mapper;

    @Override
    public ResponseEntity<ExportLogModel> createExportLog(ExportLogModel exportLogApi) {
        ExportLogModel created = mapper.toApi(service.create(mapper.toDomain(exportLogApi)));
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteExportLog(Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<ExportLogModel> getExportLogById(Long id) {
        return service.findById(id).map(mapper::toApi).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<ExportLogModel>> listExportLogs() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toApi).toList());
    }

    @Override
    public ResponseEntity<ExportLogModel> updateExportLog(Long id, ExportLogModel exportLogApi) {
        return service.update(id, mapper.toDomain(exportLogApi))
            .map(mapper::toApi)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
