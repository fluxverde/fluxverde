package io.fluxverde.domain.company.meter;

import io.fluxverde.outgoing.db.company.MeterTypeEntity;
import io.fluxverde.outgoing.db.company.MeterTypeEntityRepository;
import io.fluxverde.outgoing.db.company.MeterTypeMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MeterTypeService {
    private final MeterTypeEntityRepository repository;
    private final MeterTypeMapper mapper;

    @Transactional(readOnly = true)
    public List<MeterType> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public Optional<MeterType> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    public MeterType create(MeterType domain) {
        MeterTypeEntity entity = mapper.toEntity(domain);
        entity.setId(null);
        return mapper.toDomain(repository.save(entity));
    }

    public Optional<MeterType> update(Long id, MeterType domain) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        MeterTypeEntity entity = mapper.toEntity(domain);
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

