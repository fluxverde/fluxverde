package io.fluxverde.outgoing.db.audit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.fluxverde.outgoing.db.company.CompanyEntity;
import io.fluxverde.outgoing.db.company.CompanyMapper;
import io.fluxverde.outgoing.db.company.CompanyMapperImpl;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class EnergyBenchmarkMapperCycleTest {

    @Test
    void toDomain_usesShallowCompanyAndAvoidsRecursion() {
        CompanyMapper companyMapper = new CompanyMapperImpl();
        EnergyBenchmarkMapperImpl mapper = new EnergyBenchmarkMapperImpl();
        injectCompanyMapper(mapper, companyMapper);

        CompanyEntity company = CompanyEntity.builder()
            .id(15L)
            .companyName("Flux Verde")
            .registrationNumber("REG-15")
            .country("AT")
            .contactEmail("test@fluxverde.io")
            .build();

        EnergyBenchmarkEntity benchmark = EnergyBenchmarkEntity.builder()
            .id(2L)
            .benchmarkCode("B-1")
            .country("AT")
            .industry("Manufacturing")
            .company(company)
            .build();

        company.setBenchmarks(new ArrayList<>());
        company.getBenchmarks().add(benchmark);

        var mapped = assertDoesNotThrow(() -> mapper.toDomain(benchmark));

        assertNotNull(mapped);
        assertNotNull(mapped.company());
        assertNull(mapped.company().users());
        assertNull(mapped.company().benchmarks());
    }

    private static void injectCompanyMapper(Object target, CompanyMapper companyMapper) {
        try {
            Field field = target.getClass().getDeclaredField("companyMapper");
            field.setAccessible(true);
            field.set(target, companyMapper);
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException("Failed to inject CompanyMapper", ex);
        }
    }
}

