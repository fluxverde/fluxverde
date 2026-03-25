import { convertToParamMap, provideRouter } from '@angular/router';
import { TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { getAllByTestId, getByTestId } from '@testing-library/dom';
import { of } from 'rxjs';
import { CompanyOverviewComponent } from './company-overview.component';
import { CompanyService } from '../../services/company.service';
import { CsvUploadService } from '../../services/csv-upload.service';
import { SiteService } from '../../services/site.service';
import { ThemeService } from '../../services/theme.service';
import { CompanyModel } from '../../models/company.model';

describe('CompanyOverviewComponent', () => {
  const mockCompany: CompanyModel = {
    id: 15,
    companyName: 'GreenPort Logistics Ltd.',
    registrationNumber: 'NL-ROT-2026-005',
    country: 'NL',
    industry: 'Warehousing & Logistics',
    legalRepresentative: 'Anna van der Berg',
    employeeCount: 890,
    annualRevenueMeur: 148.5,
    totalEnergyTjPerYear: 96.2,
    regulatoryObligation: 'OVER_85_TJ',
    auditMethodology: 'ISO_50001',
    lastAuditDate: '2024-06-30',
    nextMandatoryAuditDate: '2027-06-30',
    contactEmail: 'operations@greenport-logistics.eu',
    contactPhone: '+31 10 555 1200',
    status: 'ACTIVE',
    updatedAt: '2026-03-25T12:00:00Z',
    users: [],
    benchmarks: [],
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompanyOverviewComponent],
      providers: [
        provideRouter([]),
        {
          provide: ActivatedRoute,
          useValue: {
            paramMap: of(convertToParamMap({ caseId: 'CASE-005', section: 'overview' })),
            queryParamMap: of(convertToParamMap({ companyId: '15', overviewTab: 'summary' })),
          },
        },
        {
          provide: CompanyService,
          useValue: {
            getCompanyById: () => of(mockCompany),
          },
        },
        {
          provide: CsvUploadService,
          useValue: {
            listCsvUploads: () => of([]),
          },
        },
        {
          provide: SiteService,
          useValue: {
            listSites: () => of([]),
            createSite: () => of({}),
            updateSite: () => of({}),
            getSiteTypeOptions: () => of(['WAREHOUSE', 'OFFICE']),
            getSiteStatusOptions: () => of(['ACTIVE', 'INACTIVE']),
          },
        },
        {
          provide: ThemeService,
          useValue: {
            isDarkMode: () => false,
            toggleTheme: () => undefined,
          },
        },
      ],
    }).compileComponents();
  });

  it('renders the full company card with all overview fields', async () => {
    const fixture = TestBed.createComponent(CompanyOverviewComponent);
    fixture.detectChanges();
    await fixture.whenStable();
    fixture.detectChanges();

    const element = fixture.nativeElement as HTMLElement;
    const card = getByTestId(element, 'company-card');
    expect(card).toBeTruthy();

    const fields = getAllByTestId(element, 'company-card-field');
    expect(fields).toHaveLength(16);

    const labels = getAllByTestId(element, 'company-card-field-label').map((label) =>
      label.textContent?.trim(),
    );
    const values = getAllByTestId(element, 'company-card-field-value').map((value) =>
      value.textContent?.trim(),
    );

    const labelValuePairs = labels.map((label, index) => [label, values[index]]);
    const expectedLastUpdated = new Intl.DateTimeFormat('en-US', {
      dateStyle: 'medium',
      timeStyle: 'short',
    }).format(new Date('2026-03-25T12:00:00Z'));

    expect(labelValuePairs).toEqual([
      ['Company', 'GreenPort Logistics Ltd.'],
      ['Registration Number', 'NL-ROT-2026-005'],
      ['Country', 'NL'],
      ['Industry', 'Warehousing & Logistics'],
      ['Legal Representative', 'Anna van der Berg'],
      ['Employees', '890'],
      ['Annual Revenue', '148.5 MEUR'],
      ['Energy per Year', '96.2 TJ'],
      ['Regulatory Obligation', 'Over 85 Tj'],
      ['Audit Methodology', 'Iso 50001'],
      ['Last Audit Date', 'Jun 30, 2024'],
      ['Next Mandatory Audit', 'Jun 30, 2027'],
      ['Contact Email', 'operations@greenport-logistics.eu'],
      ['Contact Phone', '+31 10 555 1200'],
      ['Status', 'Active'],
      ['Last Updated', expectedLastUpdated],
    ]);
  });
});
