package io.fluxverde.domain.regulatory;

import io.fluxverde.outgoing.db.regulatory.CalculationRuleEntity;
import io.fluxverde.outgoing.db.regulatory.CalculationRuleEntityRepository;
import io.fluxverde.outgoing.db.regulatory.CalculationRuleMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CalculationRuleService {
    private final CalculationRuleEntityRepository repository;
    private final CalculationRuleMapper mapper;

    @Transactional(readOnly = true)
    public List<CalculationRule> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public Optional<CalculationRule> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    public CalculationRule create(CalculationRule domain) {
        CalculationRuleEntity entity = mapper.toEntity(domain);
        entity.setId(null);
        return mapper.toDomain(repository.save(entity));
    }

    public Optional<CalculationRule> update(Long id, CalculationRule domain) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        CalculationRuleEntity entity = mapper.toEntity(domain);
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

