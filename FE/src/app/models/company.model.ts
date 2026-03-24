export interface SiteModel {
  id?: number;
  siteName?: string;
  siteCode?: string;
  address?: string;
  city?: string;
  postalCode?: string;
  country?: string;
  siteType?: string;
  totalAreaM2?: number;
  estimatedAnnualConsumptionTJ?: number;
  status?: string;
}

export interface CompanyUserModel {
  id?: number;
  userEmail?: string;
  firstName?: string;
  lastName?: string;
  userRole?: string;
  phoneNumber?: string;
  isActive?: boolean;
}

export interface EnergyBenchmarkModel {
  id?: number;
  benchmarkCode?: string;
  country?: string;
  industry?: string;
  siteType?: string;
  typicalEnergyUsekWhM2?: number;
  medianEnergyUsekWhM2?: number;
  bestInClasskWhM2?: number;
  sourceDataYear?: number;
}

export interface CompanyModel {
  id?: number;
  companyName?: string;
  registrationNumber?: string;
  country?: string;
  legalRepresentative?: string;
  contactEmail?: string;
  contactPhone?: string;
  industry?: string;
  employeeCount?: number;
  annualRevenueMeur?: number;
  totalEnergyTjPerYear?: number;
  regulatoryObligation?: string;
  nextMandatoryAuditDate?: string;
  lastAuditDate?: string;
  auditMethodology?: string;
  createdAt?: string;
  updatedAt?: string;
  status?: string;
  sites?: SiteModel[];
  users?: CompanyUserModel[];
  benchmarks?: EnergyBenchmarkModel[];
}
