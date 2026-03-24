package io.fluxverde.outgoing.db.company;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.fluxverde.domain.company.Company;
import io.fluxverde.outgoing.db.audit.EnergyBenchmarkEntity;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class CompanyMapperCycleTest {

    private final CompanyMapper mapper = Mappers.getMapper(CompanyMapper.class);

    @Test
    void toDomain_handlesBidirectionalReferencesWithoutStackOverflow() {
        CompanyEntity company = CompanyEntity.builder()
            .id(15L)
            .companyName("Flux Verde")
            .registrationNumber("REG-15")
            .country("AT")
            .contactEmail("test@fluxverde.io")
            .build();

        CompanyUserEntity user = CompanyUserEntity.builder()
            .id(1L)
            .userEmail("user@fluxverde.io")
            .firstName("Test")
            .lastName("User")
            .company(company)
            .build();

        EnergyBenchmarkEntity benchmark = EnergyBenchmarkEntity.builder()
            .id(2L)
            .benchmarkCode("B-1")
            .country("AT")
            .industry("Manufacturing")
            .company(company)
            .build();

        company.setUsers(new ArrayList<>());
        company.getUsers().add(user);
        company.setBenchmarks(new ArrayList<>());
        company.getBenchmarks().add(benchmark);

        Company mapped = assertDoesNotThrow(() -> mapper.toDomain(company));

        assertNotNull(mapped);
        assertNotNull(mapped.users());
        assertNotNull(mapped.benchmarks());
        assertNotNull(mapped.users().getFirst().company());
        assertNull(mapped.users().getFirst().company().users());
        assertNull(mapped.users().getFirst().company().benchmarks());
        assertNull(mapped.benchmarks().getFirst().company().users());
        assertNull(mapped.benchmarks().getFirst().company().benchmarks());
    }
}

