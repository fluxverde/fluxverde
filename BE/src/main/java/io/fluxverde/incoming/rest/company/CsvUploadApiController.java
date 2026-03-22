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
    public ResponseEntity<CSVUploadModel> createCsvUpload(CSVUploadModel csvUploadApi) {
        CSVUploadModel created = mapper.toApi(service.create(mapper.toDomain(csvUploadApi)));
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteCsvUpload(Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<CSVUploadModel> getCsvUploadById(Long id) {
        return service.findById(id).map(mapper::toApi).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<CSVUploadModel>> listCsvUploads() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toApi).toList());
    }

    @Override
    public ResponseEntity<CSVUploadModel> updateCsvUpload(Long id, CSVUploadModel csvUploadApi) {
        return service.update(id, mapper.toDomain(csvUploadApi))
            .map(mapper::toApi)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
