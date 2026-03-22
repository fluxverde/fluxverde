package io.fluxverde.domain.company;

import io.fluxverde.outgoing.db.company.CompanyUserEntity;
import io.fluxverde.outgoing.db.company.CompanyUserEntityRepository;
import io.fluxverde.outgoing.db.company.CompanyUserMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyUserService {
    private final CompanyUserEntityRepository repository;
    private final CompanyUserMapper mapper;

    @Transactional(readOnly = true)
    public List<CompanyUser> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public Optional<CompanyUser> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    public CompanyUser create(CompanyUser domain) {
        CompanyUserEntity entity = mapper.toEntity(domain);
        entity.setId(null);
        return mapper.toDomain(repository.save(entity));
    }

    public Optional<CompanyUser> update(Long id, CompanyUser domain) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        CompanyUserEntity entity = mapper.toEntity(domain);
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

