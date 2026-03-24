package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.CSVUploadService;
import io.fluxverde.rest.api.CsvUploadApi;
import io.fluxverde.rest.model.CSVUploadModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CsvUploadApiController implements CsvUploadApi {
    private final CSVUploadService service;
    private final CSVUploadApiMapper mapper;

    @Override
    public ResponseEntity<CSVUploadModel> createCSVUpload(CSVUploadModel csVUploadModel) {
        CSVUploadModel created = mapper.toApi(service.create(mapper.toDomain(csVUploadModel)));
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteCSVUpload(Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<CSVUploadModel> getCSVUploadById(Long id) {
        return service.findById(id).map(mapper::toApi).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<CSVUploadModel>> listCSVUploads() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toApi).toList());
    }

    @Override
    public ResponseEntity<CSVUploadModel> updateCSVUpload(Long id, CSVUploadModel csVUploadModel) {
        return service.update(id, mapper.toDomain(csVUploadModel))
            .map(mapper::toApi)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
