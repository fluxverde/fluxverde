package io.fluxverde.domain.company.meter;

import io.fluxverde.outgoing.db.company.MeterEntity;
import io.fluxverde.outgoing.db.company.MeterEntityRepository;
import io.fluxverde.outgoing.db.company.MeterMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MeterService {
    private final MeterEntityRepository repository;
    private final MeterMapper mapper;

    @Transactional(readOnly = true)
    public List<Meter> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public Optional<Meter> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    public Meter create(Meter domain) {
        MeterEntity entity = mapper.toEntity(domain);
        entity.setId(null);
        return mapper.toDomain(repository.save(entity));
    }

    public Optional<Meter> update(Long id, Meter domain) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        MeterEntity entity = mapper.toEntity(domain);
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

