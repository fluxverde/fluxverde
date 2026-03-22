package io.fluxverde.domain.company.meter;

import io.fluxverde.outgoing.db.company.ManualMeterEntryEntity;
import io.fluxverde.outgoing.db.company.ManualMeterEntryEntityRepository;
import io.fluxverde.outgoing.db.company.ManualMeterEntryMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ManualMeterEntryService {
    private final ManualMeterEntryEntityRepository repository;
    private final ManualMeterEntryMapper mapper;

    @Transactional(readOnly = true)
    public List<ManualMeterEntry> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public Optional<ManualMeterEntry> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    public ManualMeterEntry create(ManualMeterEntry domain) {
        ManualMeterEntryEntity entity = mapper.toEntity(domain);
        entity.setId(null);
        return mapper.toDomain(repository.save(entity));
    }

    public Optional<ManualMeterEntry> update(Long id, ManualMeterEntry domain) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        ManualMeterEntryEntity entity = mapper.toEntity(domain);
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

