package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.meter.MeterReadingService;
import io.fluxverde.rest.api.MeterReadingApi;
import io.fluxverde.rest.model.MeterReadingModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MeterReadingApiController implements MeterReadingApi {
    private final MeterReadingService service;
    private final MeterReadingApiMapper mapper;

    @Override
    public ResponseEntity<MeterReadingModel> createMeterReading(MeterReadingModel meterReadingApi) {
        MeterReadingModel created = mapper.toApi(service.create(mapper.toDomain(meterReadingApi)));
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteMeterReading(Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<MeterReadingModel> getMeterReadingById(Long id) {
        return service.findById(id).map(mapper::toApi).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<MeterReadingModel>> listMeterReadings() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toApi).toList());
    }

    @Override
    public ResponseEntity<MeterReadingModel> updateMeterReading(Long id, MeterReadingModel meterReadingApi) {
        return service.update(id, mapper.toDomain(meterReadingApi))
            .map(mapper::toApi)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
