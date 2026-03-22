package io.fluxverde.domain.company.meter;

import io.fluxverde.outgoing.db.company.MeterReadingEntity;
import io.fluxverde.outgoing.db.company.MeterReadingEntityRepository;
import io.fluxverde.outgoing.db.company.MeterReadingMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MeterReadingService {
    private final MeterReadingEntityRepository repository;
    private final MeterReadingMapper mapper;

    @Transactional(readOnly = true)
    public List<MeterReading> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public Optional<MeterReading> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    public MeterReading create(MeterReading domain) {
        MeterReadingEntity entity = mapper.toEntity(domain);
        entity.setId(null);
        return mapper.toDomain(repository.save(entity));
    }

    public Optional<MeterReading> update(Long id, MeterReading domain) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        MeterReadingEntity entity = mapper.toEntity(domain);
        entity.setId(id);
        return Optional.of(mapper.toDomain(repository.save(entity)));
    }

    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}

