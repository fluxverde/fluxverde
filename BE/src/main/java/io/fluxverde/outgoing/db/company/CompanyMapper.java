package io.fluxverde.outgoing.db.company;

import io.fluxverde.domain.company.Company;
import io.fluxverde.domain.company.CompanyUser;
import io.fluxverde.domain.visualization.EnergyBenchmark;
import io.fluxverde.outgoing.db.audit.EnergyBenchmarkEntity;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @Mapping(target = "users", source = "users", qualifiedByName = "companyUserEntityListToCompanyUserListSafe")
    @Mapping(target = "benchmarks", source = "benchmarks", qualifiedByName = "energyBenchmarkEntityListToEnergyBenchmarkListSafe")
    Company toDomain(CompanyEntity entity);

    @Mapping(target = "users", source = "users", qualifiedByName = "companyUserListToCompanyUserEntityListSafe")
    @Mapping(target = "benchmarks", source = "benchmarks", qualifiedByName = "energyBenchmarkListToEnergyBenchmarkEntityListSafe")
    CompanyEntity toEntity(Company domain);

    @Named("toDomainShallow")
    @Mapping(target = "sites", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "benchmarks", ignore = true)
    Company toDomainShallow(CompanyEntity entity);

    @Named("toEntityShallow")
    @Mapping(target = "sites", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "benchmarks", ignore = true)
    CompanyEntity toEntityShallow(Company domain);

    @Named("companyUserEntityListToCompanyUserListSafe")
    @IterableMapping(qualifiedByName = "companyUserEntityToCompanyUserSafe")
    List<CompanyUser> companyUserEntityListToCompanyUserListSafe(List<CompanyUserEntity> entities);

    @Named("companyUserEntityToCompanyUserSafe")
    @Mapping(target = "company", source = "company", qualifiedByName = "toDomainShallow")
    CompanyUser companyUserEntityToCompanyUserSafe(CompanyUserEntity entity);

    @Named("companyUserListToCompanyUserEntityListSafe")
    @IterableMapping(qualifiedByName = "companyUserToCompanyUserEntitySafe")
    List<CompanyUserEntity> companyUserListToCompanyUserEntityListSafe(List<CompanyUser> domains);

    @Named("companyUserToCompanyUserEntitySafe")
    @Mapping(target = "company", source = "company", qualifiedByName = "toEntityShallow")
    CompanyUserEntity companyUserToCompanyUserEntitySafe(CompanyUser domain);

    @Named("energyBenchmarkEntityListToEnergyBenchmarkListSafe")
    @IterableMapping(qualifiedByName = "energyBenchmarkEntityToEnergyBenchmarkSafe")
    List<EnergyBenchmark> energyBenchmarkEntityListToEnergyBenchmarkListSafe(List<EnergyBenchmarkEntity> entities);

    @Named("energyBenchmarkEntityToEnergyBenchmarkSafe")
    @Mapping(target = "company", source = "company", qualifiedByName = "toDomainShallow")
    EnergyBenchmark energyBenchmarkEntityToEnergyBenchmarkSafe(EnergyBenchmarkEntity entity);

    @Named("energyBenchmarkListToEnergyBenchmarkEntityListSafe")
    @IterableMapping(qualifiedByName = "energyBenchmarkToEnergyBenchmarkEntitySafe")
    List<EnergyBenchmarkEntity> energyBenchmarkListToEnergyBenchmarkEntityListSafe(List<EnergyBenchmark> domains);

    @Named("energyBenchmarkToEnergyBenchmarkEntitySafe")
    @Mapping(target = "company", source = "company", qualifiedByName = "toEntityShallow")
    EnergyBenchmarkEntity energyBenchmarkToEnergyBenchmarkEntitySafe(EnergyBenchmark domain);
}
