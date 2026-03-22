package io.fluxverde.domain.visualization;

import io.fluxverde.outgoing.db.audit.EnergyBenchmarkEntity;
import io.fluxverde.outgoing.db.audit.EnergyBenchmarkEntityRepository;
import io.fluxverde.outgoing.db.audit.EnergyBenchmarkMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EnergyBenchmarkService {
    private final EnergyBenchmarkEntityRepository repository;
    private final EnergyBenchmarkMapper mapper;

    @Transactional(readOnly = true)
    public List<EnergyBenchmark> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public Optional<EnergyBenchmark> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    public EnergyBenchmark create(EnergyBenchmark domain) {
        EnergyBenchmarkEntity entity = mapper.toEntity(domain);
        entity.setId(null);
        return mapper.toDomain(repository.save(entity));
    }

    public Optional<EnergyBenchmark> update(Long id, EnergyBenchmark domain) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        EnergyBenchmarkEntity entity = mapper.toEntity(domain);
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

