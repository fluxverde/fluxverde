package io.fluxverde.outgoing.db.company;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    Optional<CompanyEntity> findByCompanyName(String companyName);
    Optional<CompanyEntity> findByRegistrationNumber(String registrationNumber);
}
