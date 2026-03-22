package io.fluxverde.domain.company;

import io.fluxverde.outgoing.db.company.CompanyEntity;
import io.fluxverde.outgoing.db.company.CompanyMapper;
import io.fluxverde.outgoing.db.company.CompanyRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyService {
    private final CompanyRepository repository;
    private final CompanyMapper mapper;

    @Transactional(readOnly = true)
    public List<Company> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public Optional<Company> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    public Company create(Company domain) {
        CompanyEntity entity = mapper.toEntity(domain);
        entity.setId(null);
        return mapper.toDomain(repository.save(entity));
    }

    public Optional<Company> update(Long id, Company domain) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        CompanyEntity entity = mapper.toEntity(domain);
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

