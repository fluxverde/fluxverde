package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.meter.MeterService;
import io.fluxverde.rest.api.MeterApi;
import io.fluxverde.rest.model.MeterModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MeterApiController implements MeterApi {
    private final MeterService service;
    private final MeterApiMapper mapper;

    @Override
    public ResponseEntity<MeterModel> createMeter(MeterModel meterApi) {
        MeterModel created = mapper.toApi(service.create(mapper.toDomain(meterApi)));
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteMeter(Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<MeterModel> getMeterById(Long id) {
        return service.findById(id).map(mapper::toApi).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<MeterModel>> listMeters() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toApi).toList());
    }

    @Override
    public ResponseEntity<MeterModel> updateMeter(Long id, MeterModel meterApi) {
        return service.update(id, mapper.toDomain(meterApi))
            .map(mapper::toApi)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
