package io.fluxverde.incoming.rest.report;

import io.fluxverde.domain.company.audit.ReportGenerationService;
import io.fluxverde.rest.api.ReportGenerationApi;
import io.fluxverde.rest.model.ReportGenerationModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportGenerationApiController implements ReportGenerationApi {
    private final ReportGenerationService service;
    private final ReportGenerationApiMapper mapper;

    @Override
    public ResponseEntity<ReportGenerationModel> createReportGeneration(ReportGenerationModel reportGenerationApi) {
        ReportGenerationModel created = mapper.toApi(service.create(mapper.toDomain(reportGenerationApi)));
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteReportGeneration(Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<ReportGenerationModel> getReportGenerationById(Long id) {
        return service.findById(id).map(mapper::toApi).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<ReportGenerationModel>> listReportGenerations() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toApi).toList());
    }

    @Override
    public ResponseEntity<ReportGenerationModel> updateReportGeneration(Long id, ReportGenerationModel reportGenerationApi) {
        return service.update(id, mapper.toDomain(reportGenerationApi))
            .map(mapper::toApi)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
