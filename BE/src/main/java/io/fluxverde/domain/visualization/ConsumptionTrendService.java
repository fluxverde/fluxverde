package io.fluxverde.domain.visualization;

import io.fluxverde.outgoing.db.visualization.ConsumptionTrendEntity;
import io.fluxverde.outgoing.db.visualization.ConsumptionTrendEntityRepository;
import io.fluxverde.outgoing.db.visualization.ConsumptionTrendMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ConsumptionTrendService {
    private final ConsumptionTrendEntityRepository repository;
    private final ConsumptionTrendMapper mapper;

    @Transactional(readOnly = true)
    public List<ConsumptionTrend> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public Optional<ConsumptionTrend> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    public ConsumptionTrend create(ConsumptionTrend domain) {
        ConsumptionTrendEntity entity = mapper.toEntity(domain);
        entity.setId(null);
        return mapper.toDomain(repository.save(entity));
    }

    public Optional<ConsumptionTrend> update(Long id, ConsumptionTrend domain) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        ConsumptionTrendEntity entity = mapper.toEntity(domain);
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

