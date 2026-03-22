package io.fluxverde.domain.regulatory;

import io.fluxverde.outgoing.db.regulatory.CountryRequirementEntity;
import io.fluxverde.outgoing.db.regulatory.CountryRequirementEntityRepository;
import io.fluxverde.outgoing.db.regulatory.CountryRequirementMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CountryRequirementService {
    private final CountryRequirementEntityRepository repository;
    private final CountryRequirementMapper mapper;

    @Transactional(readOnly = true)
    public List<CountryRequirement> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public Optional<CountryRequirement> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    public CountryRequirement create(CountryRequirement domain) {
        CountryRequirementEntity entity = mapper.toEntity(domain);
        entity.setId(null);
        return mapper.toDomain(repository.save(entity));
    }

    public Optional<CountryRequirement> update(Long id, CountryRequirement domain) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        CountryRequirementEntity entity = mapper.toEntity(domain);
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

