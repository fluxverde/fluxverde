import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, DestroyRef, computed, inject, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { DrawerModule } from 'primeng/drawer';
import { combineLatest } from 'rxjs';
import { CompanyModel } from '../../models/company.model';
import { CSVUploadModel } from '../../models/csv-upload.model';
import { CompanyService } from '../../services/company.service';
import { CsvUploadService } from '../../services/csv-upload.service';
import { ThemeService } from '../../services/theme.service';
import { CsvUploadTaskListComponent } from '../evidence/csv-upload-task-list.component';

interface CompanyField {
  label: string;
  value: string;
}

interface NavigationItem {
  label: string;
  section: string;
}

@Component({
  selector: 'app-company-overview',
  standalone: true,
  imports: [CommonModule, RouterLink, DrawerModule, CsvUploadTaskListComponent],
  templateUrl: './company-overview.component.html',
  styleUrl: './company-overview.component.scss',
})
export class CompanyOverviewComponent {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly destroyRef = inject(DestroyRef);
  private readonly companyService = inject(CompanyService);
  private readonly csvUploadService = inject(CsvUploadService);
  private readonly themeService = inject(ThemeService);

  protected readonly company = signal<CompanyModel | null>(null);
  protected readonly caseId = signal('CASE-005');
  protected readonly companyId = signal(1);
  protected readonly companyIdInput = signal('1');
  protected readonly currentSection = signal('overview');
  protected readonly drawerOpen = signal(true);
  protected readonly isLoading = signal(true);
  protected readonly errorMessage = signal<string | null>(null);
  protected readonly csvUploads = signal<CSVUploadModel[]>([]);
  protected readonly csvUploadsLoading = signal(false);
  protected readonly csvUploadsError = signal<string | null>(null);
  protected readonly isDarkMode = computed(() => this.themeService.isDarkMode());
  protected readonly navigationItems: NavigationItem[] = [
    { label: 'Overview', section: 'overview' },
    { label: 'Documents', section: 'documents' },
    { label: 'Evidence Ledger', section: 'evidence' },
    { label: 'Review Queue', section: 'review-queue' },
    { label: 'Analytics', section: 'analytics' },
    { label: 'Measures', section: 'measures' },
    { label: 'Audit Report', section: 'audit-report' },
    { label: 'Template', section: 'template' },
    { label: 'Exports', section: 'exports' },
    { label: 'Audit Log', section: 'audit-log' },
  ];
  protected readonly isOverviewSection = computed(() => this.currentSection() === 'overview');
  protected readonly isEvidenceSection = computed(() => this.currentSection() === 'evidence');
  protected readonly currentSectionLabel = computed(
    () =>
      this.navigationItems.find((item) => item.section === this.currentSection())?.label ??
      'Overview',
  );

  protected readonly companyFields = computed<CompanyField[]>(() => {
    const company = this.company();

    if (!company) {
      return [];
    }

    return [
      { label: 'Company', value: this.fallback(company.companyName) },
      { label: 'Registration Number', value: this.fallback(company.registrationNumber) },
      { label: 'Country', value: this.fallback(company.country) },
      { label: 'Industry', value: this.fallback(company.industry) },
      { label: 'Legal Representative', value: this.fallback(company.legalRepresentative) },
      { label: 'Employees', value: this.formatNumber(company.employeeCount) },
      { label: 'Annual Revenue', value: this.formatRevenue(company.annualRevenueMeur) },
      { label: 'Energy per Year', value: this.formatEnergy(company.totalEnergyTjPerYear) },
      { label: 'Regulatory Obligation', value: this.formatEnum(company.regulatoryObligation) },
      { label: 'Audit Methodology', value: this.formatEnum(company.auditMethodology) },
      { label: 'Last Audit Date', value: this.formatDate(company.lastAuditDate) },
      { label: 'Next Mandatory Audit', value: this.formatDate(company.nextMandatoryAuditDate) },
      { label: 'Contact Email', value: this.fallback(company.contactEmail) },
      { label: 'Contact Phone', value: this.fallback(company.contactPhone) },
      { label: 'Status', value: this.formatEnum(company.status) },
      { label: 'Last Updated', value: this.formatDateTime(company.updatedAt) },
    ];
  });

  constructor() {
    combineLatest([this.route.paramMap, this.route.queryParamMap])
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(([params, queryParams]) => {
        const caseId = params.get('caseId') ?? 'CASE-005';
        const currentSection = params.get('section') ?? 'overview';
        const companyId = Number.parseInt(queryParams.get('companyId') ?? '1', 10);
        const sanitizedCompanyId = Number.isFinite(companyId) && companyId > 0 ? companyId : 1;

        this.caseId.set(caseId);
        this.currentSection.set(currentSection);
        this.companyId.set(sanitizedCompanyId);
        this.companyIdInput.set(String(sanitizedCompanyId));
        this.loadCompany(sanitizedCompanyId);

        if (currentSection === 'evidence') {
          this.loadCsvUploads();
        }
      });
  }

  protected toggleTheme(): void {
    this.themeService.toggleTheme();
  }

  protected updateCompanyIdInput(value: string): void {
    this.companyIdInput.set(value);
  }

  protected applyCompanyId(): void {
    const nextCompanyId = Number.parseInt(this.companyIdInput(), 10);

    if (!Number.isFinite(nextCompanyId) || nextCompanyId <= 0) {
      this.errorMessage.set('Enter a valid numeric company id.');
      return;
    }

    void this.router.navigate([], {
      relativeTo: this.route,
      queryParams: { companyId: nextCompanyId },
      queryParamsHandling: 'merge',
    });
  }

  protected readonly siteCount = computed(() => this.company()?.sites?.length ?? 0);
  protected readonly userCount = computed(() => this.company()?.users?.length ?? 0);
  protected readonly benchmarkCount = computed(() => this.company()?.benchmarks?.length ?? 0);

  protected toggleSidebar(): void {
    this.drawerOpen.update((value) => !value);
  }

  protected setDrawerOpen(value: boolean): void {
    this.drawerOpen.set(value);
  }

  private loadCompany(companyId: number): void {
    this.isLoading.set(true);
    this.errorMessage.set(null);
    this.company.set(null);

    this.companyService
      .getCompanyById(companyId)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (company) => {
          this.company.set(company);
          this.isLoading.set(false);
        },
        error: (error: HttpErrorResponse) => {
          this.isLoading.set(false);

          if (error.status === 404) {
            this.errorMessage.set(
              `No company record was found for id ${companyId}. The backend currently exposes the endpoint, but it does not seem to have seeded company data.`,
            );
            return;
          }

          this.errorMessage.set(
            error.error?.message ??
              error.message ??
              'The company endpoint could not be reached.',
          );
        },
      });
  }

  private loadCsvUploads(): void {
    this.csvUploadsLoading.set(true);
    this.csvUploadsError.set(null);

    this.csvUploadService
      .listCsvUploads()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (uploads) => {
          this.csvUploads.set(uploads);
          this.csvUploadsLoading.set(false);
        },
        error: (error: HttpErrorResponse) => {
          this.csvUploadsLoading.set(false);
          this.csvUploadsError.set(
            error.error?.message ??
              error.message ??
              'The CSV uploads endpoint could not be reached.',
          );
        },
      });
  }

  private fallback(value?: string | null): string {
    return value?.trim() ? value : '—';
  }

  private formatNumber(value?: number | null): string {
    if (value === null || value === undefined) {
      return '—';
    }

    return new Intl.NumberFormat('en-US').format(value);
  }

  private formatRevenue(value?: number | null): string {
    if (value === null || value === undefined) {
      return '—';
    }

    return `${new Intl.NumberFormat('en-US', {
      minimumFractionDigits: 0,
      maximumFractionDigits: 2,
    }).format(value)} MEUR`;
  }

  private formatEnergy(value?: number | null): string {
    if (value === null || value === undefined) {
      return '—';
    }

    return `${new Intl.NumberFormat('en-US', {
      minimumFractionDigits: 0,
      maximumFractionDigits: 2,
    }).format(value)} TJ`;
  }

  private formatDate(value?: string | null): string {
    if (!value) {
      return '—';
    }

    return new Intl.DateTimeFormat('en-US', { dateStyle: 'medium' }).format(new Date(value));
  }

  private formatDateTime(value?: string | null): string {
    if (!value) {
      return '—';
    }

    return new Intl.DateTimeFormat('en-US', {
      dateStyle: 'medium',
      timeStyle: 'short',
    }).format(new Date(value));
  }

  private formatEnum(value?: string | null): string {
    if (!value) {
      return '—';
    }

    return value
      .toLowerCase()
      .split('_')
      .map((segment) => segment.charAt(0).toUpperCase() + segment.slice(1))
      .join(' ');
  }
}
