package io.fluxverde.domain.company.audit;

import io.fluxverde.outgoing.db.audit.EnergyAuditEntity;
import io.fluxverde.outgoing.db.audit.EnergyAuditEntityRepository;
import io.fluxverde.outgoing.db.audit.EnergyAuditMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EnergyAuditService {
    private final EnergyAuditEntityRepository repository;
    private final EnergyAuditMapper mapper;

    @Transactional(readOnly = true)
    public List<EnergyAudit> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public Optional<EnergyAudit> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    public EnergyAudit create(EnergyAudit domain) {
        EnergyAuditEntity entity = mapper.toEntity(domain);
        entity.setId(null);
        return mapper.toDomain(repository.save(entity));
    }

    public Optional<EnergyAudit> update(Long id, EnergyAudit domain) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        EnergyAuditEntity entity = mapper.toEntity(domain);
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

