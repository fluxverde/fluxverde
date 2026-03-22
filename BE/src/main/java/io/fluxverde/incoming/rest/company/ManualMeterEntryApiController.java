package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.meter.ManualMeterEntryService;
import io.fluxverde.rest.api.ManualMeterEntryApi;
import io.fluxverde.rest.model.ManualMeterEntryModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ManualMeterEntryApiController implements ManualMeterEntryApi {
    private final ManualMeterEntryService service;
    private final ManualMeterEntryApiMapper mapper;

    @Override
    public ResponseEntity<ManualMeterEntryModel> createManualMeterEntry(ManualMeterEntryModel manualMeterEntryApi) {
        ManualMeterEntryModel created = mapper.toApi(service.create(mapper.toDomain(manualMeterEntryApi)));
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteManualMeterEntry(Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<ManualMeterEntryModel> getManualMeterEntryById(Long id) {
        return service.findById(id).map(mapper::toApi).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<ManualMeterEntryModel>> listManualMeterEntrys() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toApi).toList());
    }

    @Override
    public ResponseEntity<ManualMeterEntryModel> updateManualMeterEntry(Long id, ManualMeterEntryModel manualMeterEntryApi) {
        return service.update(id, mapper.toDomain(manualMeterEntryApi))
            .map(mapper::toApi)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
