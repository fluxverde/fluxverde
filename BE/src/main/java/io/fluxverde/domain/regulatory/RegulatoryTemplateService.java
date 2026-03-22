package io.fluxverde.domain.regulatory;

import io.fluxverde.outgoing.db.regulatory.RegulatoryTemplateEntity;
import io.fluxverde.outgoing.db.regulatory.RegulatoryTemplateEntityRepository;
import io.fluxverde.outgoing.db.regulatory.RegulatoryTemplateMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegulatoryTemplateService {
    private final RegulatoryTemplateEntityRepository repository;
    private final RegulatoryTemplateMapper mapper;

    @Transactional(readOnly = true)
    public List<RegulatoryTemplate> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public Optional<RegulatoryTemplate> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    public RegulatoryTemplate create(RegulatoryTemplate domain) {
        RegulatoryTemplateEntity entity = mapper.toEntity(domain);
        entity.setId(null);
        return mapper.toDomain(repository.save(entity));
    }

    public Optional<RegulatoryTemplate> update(Long id, RegulatoryTemplate domain) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        RegulatoryTemplateEntity entity = mapper.toEntity(domain);
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

