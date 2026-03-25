import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, DestroyRef, computed, inject, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { DrawerModule } from 'primeng/drawer';
import { combineLatest } from 'rxjs';
import { CompanyModel, CreateSiteRequest, SiteModel } from '../../models/company.model';
import { CSVUploadModel } from '../../models/csv-upload.model';
import { CompanyService } from '../../services/company.service';
import { CsvUploadService } from '../../services/csv-upload.service';
import { SiteService } from '../../services/site.service';
import { ThemeService } from '../../services/theme.service';
import { AuditProcessComponent } from '../audit-process/audit-process.component';
import { CsvUploadTaskListComponent } from '../evidence/csv-upload-task-list.component';

interface CompanyField {
  label: string;
  value: string;
}

interface NavigationItem {
  label: string;
  section: string;
}

interface OverviewSubsection {
  label: string;
  key: 'summary' | 'sites';
}

@Component({
  selector: 'app-company-overview',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    DrawerModule,
    ReactiveFormsModule,
    CsvUploadTaskListComponent,
    AuditProcessComponent,
  ],
  templateUrl: './company-overview.component.html',
  styleUrl: './company-overview.component.scss',
})
export class CompanyOverviewComponent {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly destroyRef = inject(DestroyRef);
  private readonly formBuilder = inject(FormBuilder);
  private readonly companyService = inject(CompanyService);
  private readonly csvUploadService = inject(CsvUploadService);
  private readonly siteService = inject(SiteService);
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
  protected readonly overviewSubsection = signal<'summary' | 'sites'>('summary');
  protected readonly sites = signal<SiteModel[]>([]);
  protected readonly siteEditorMode = signal<'list' | 'create' | 'edit'>('list');
  protected readonly editingSiteId = signal<number | null>(null);
  protected readonly siteSubmitError = signal<string | null>(null);
  protected readonly siteSubmitSuccess = signal<string | null>(null);
  protected readonly siteSubmitting = signal(false);
  protected readonly sitesLoading = signal(false);
  protected readonly sitesError = signal<string | null>(null);
  protected readonly sitesLoadedCompanyId = signal<number | null>(null);
  protected readonly siteTypeOptions = signal<string[]>([]);
  protected readonly siteTypesLoading = signal(false);
  protected readonly siteTypesError = signal<string | null>(null);
  protected readonly siteStatusOptions = signal<string[]>([]);
  protected readonly siteStatusesLoading = signal(false);
  protected readonly siteStatusesError = signal<string | null>(null);
  protected readonly isDarkMode = computed(() => this.themeService.isDarkMode());
  protected readonly navigationItems: NavigationItem[] = [
    { label: 'Overview', section: 'overview' },
    { label: 'Audit Journey', section: 'audit-process' },
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
  protected readonly overviewSubsections: OverviewSubsection[] = [
    { label: 'Summary', key: 'summary' },
    { label: 'Sites', key: 'sites' },
  ];
  protected readonly isOverviewSection = computed(() => this.currentSection() === 'overview');
  protected readonly isAuditProcessSection = computed(() => this.currentSection() === 'audit-process');
  protected readonly isEvidenceSection = computed(() => this.currentSection() === 'evidence');
  protected readonly isOverviewSummary = computed(() => this.overviewSubsection() === 'summary');
  protected readonly isOverviewSites = computed(() => this.overviewSubsection() === 'sites');
  protected readonly isSiteListMode = computed(() => this.siteEditorMode() === 'list');
  protected readonly isSiteCreateMode = computed(() => this.siteEditorMode() === 'create');
  protected readonly isSiteEditMode = computed(() => this.siteEditorMode() === 'edit');
  protected readonly siteFormTitle = computed(() =>
    this.isSiteEditMode() ? 'Edit company site' : 'Register a company site',
  );
  protected readonly siteFormEyebrow = computed(() =>
    this.isSiteEditMode() ? 'Edit site' : 'Add site',
  );
  protected readonly siteSubmitLabel = computed(() => {
    if (this.siteSubmitting()) {
      return this.isSiteEditMode() ? 'Saving changes...' : 'Creating site...';
    }

    return this.isSiteEditMode() ? 'Save changes' : 'Create site';
  });
  protected readonly currentSectionLabel = computed(
    () =>
      this.navigationItems.find((item) => item.section === this.currentSection())?.label ??
      'Overview',
  );
  protected readonly hasSites = computed(() => this.sites().length > 0);

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

  protected readonly siteForm = this.formBuilder.nonNullable.group({
    siteName: ['', [Validators.required]],
    siteCode: ['', [Validators.required]],
    city: [''],
    country: ['AT', [Validators.required, Validators.minLength(2), Validators.maxLength(2)]],
    address: [''],
    postalCode: [''],
    siteType: [''],
    status: ['ACTIVE'],
    productionProcess: [''],
    totalAreaM2: [null as number | null],
    estimatedAnnualConsumptionTJ: [null as number | null],
  });

  constructor() {
    combineLatest([this.route.paramMap, this.route.queryParamMap])
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(([params, queryParams]) => {
        const previousCompanyId = this.companyId();
        const previousSection = this.currentSection();
        const caseId = params.get('caseId') ?? 'CASE-005';
        const currentSection = params.get('section') ?? 'overview';
        const companyId = Number.parseInt(queryParams.get('companyId') ?? '1', 10);
        const sanitizedCompanyId = Number.isFinite(companyId) && companyId > 0 ? companyId : 1;
        const requestedOverviewSubsection = queryParams.get('overviewTab');
        const overviewSubsection =
          requestedOverviewSubsection === 'sites' ? 'sites' : 'summary';
        const companyChanged = previousCompanyId !== sanitizedCompanyId || !this.company();

        this.caseId.set(caseId);
        this.currentSection.set(currentSection);
        this.overviewSubsection.set(overviewSubsection);
        this.companyId.set(sanitizedCompanyId);
        this.companyIdInput.set(String(sanitizedCompanyId));

        if (companyChanged) {
          this.loadCompany(sanitizedCompanyId);
        }

        this.ensureSiteTypesLoaded();
        this.ensureSiteStatusesLoaded();

        if (currentSection === 'overview' && overviewSubsection === 'sites') {
          this.ensureSitesLoaded();
        }

        if (
          currentSection === 'evidence' ||
          currentSection === 'audit-process' ||
          previousSection !== currentSection
        ) {
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

  protected setOverviewSubsection(subsection: 'summary' | 'sites'): void {
    this.overviewSubsection.set(subsection);

    if (subsection === 'sites') {
      this.ensureSitesLoaded();
    }

    void this.router.navigate([], {
      relativeTo: this.route,
      queryParams: { overviewTab: subsection },
      queryParamsHandling: 'merge',
    });
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

  protected readonly siteCount = computed(() => this.sites().length);
  protected readonly userCount = computed(() => this.company()?.users?.length ?? 0);
  protected readonly benchmarkCount = computed(() => this.company()?.benchmarks?.length ?? 0);

  protected toggleSidebar(): void {
    this.drawerOpen.update((value) => !value);
  }

  protected setDrawerOpen(value: boolean): void {
    this.drawerOpen.set(value);
  }

  protected submitSite(): void {
    if (this.siteForm.invalid) {
      this.siteForm.markAllAsTouched();
      return;
    }

    const formValue = this.siteForm.getRawValue();
    const payload: CreateSiteRequest = {
      siteName: formValue.siteName.trim(),
      siteCode: formValue.siteCode.trim(),
      city: this.optionalValue(formValue.city),
      country: formValue.country.trim().toUpperCase(),
      address: this.optionalValue(formValue.address),
      postalCode: this.optionalValue(formValue.postalCode),
      siteType: this.optionalValue(formValue.siteType),
      status: this.optionalValue(formValue.status),
      productionProcess: this.optionalValue(formValue.productionProcess),
      totalAreaM2: formValue.totalAreaM2,
      estimatedAnnualConsumptionTJ: formValue.estimatedAnnualConsumptionTJ,
      companyId: this.companyId(),
    };

    this.siteSubmitting.set(true);
    this.siteSubmitError.set(null);
    this.siteSubmitSuccess.set(null);

    const editingSiteId = this.editingSiteId();
    const request =
      this.isSiteEditMode() && editingSiteId
        ? this.siteService.updateSite(editingSiteId, payload)
        : this.siteService.createSite(payload);

    request.pipe(takeUntilDestroyed(this.destroyRef)).subscribe({
      next: (savedSite) => {
        const site = { ...savedSite, companyId: savedSite.companyId ?? this.companyId() };
        this.sites.update((sites) => this.upsertSite(sites, site));
        this.siteSubmitting.set(false);
        this.siteSubmitSuccess.set(
          this.isSiteEditMode()
            ? `Site ${site.siteName ?? payload.siteName} updated.`
            : `Site ${site.siteName ?? payload.siteName} created.`,
        );
        this.closeSiteForm();
      },
      error: (error: HttpErrorResponse) => {
        this.siteSubmitting.set(false);
        this.siteSubmitError.set(
          error.error?.message ??
            error.message ??
            (this.isSiteEditMode()
              ? 'The site could not be updated. Verify that the backend is reachable and the payload matches the API.'
              : 'The site could not be created. Verify that the backend is reachable and the payload matches the API.'),
        );
      },
    });
  }

  protected openCreateSiteForm(): void {
    this.siteEditorMode.set('create');
    this.editingSiteId.set(null);
    this.siteSubmitError.set(null);
    this.siteSubmitSuccess.set(null);
    this.resetSiteForm();
  }

  protected openEditSiteForm(site: SiteModel): void {
    this.siteEditorMode.set('edit');
    this.editingSiteId.set(site.id ?? null);
    this.siteSubmitError.set(null);
    this.siteSubmitSuccess.set(null);
    this.siteForm.reset({
      siteName: site.siteName ?? '',
      siteCode: site.siteCode ?? '',
      city: site.city ?? '',
      country: site.country ?? 'AT',
      address: site.address ?? '',
      postalCode: site.postalCode ?? '',
      siteType: site.siteType ?? '',
      status: site.status ?? 'ACTIVE',
      productionProcess: site.productionProcess ?? '',
      totalAreaM2: site.totalAreaM2 ?? null,
      estimatedAnnualConsumptionTJ: site.estimatedAnnualConsumptionTJ ?? null,
    });
  }

  protected cancelSiteForm(): void {
    this.siteSubmitError.set(null);
    this.closeSiteForm();
  }

  protected formatSiteLocation(site: SiteModel): string {
    return [site.city, site.country].filter(Boolean).join(', ') || '—';
  }

  protected trackSite(_: number, site: SiteModel): string | number {
    return site.id ?? site.siteCode ?? site.siteName ?? _;
  }

  protected hasSiteFieldError(controlName: keyof typeof this.siteForm.controls): boolean {
    const control = this.siteForm.controls[controlName];
    return control.invalid && (control.dirty || control.touched);
  }

  protected formatSiteTypeOption(value: string): string {
    return this.formatEnum(value);
  }

  private loadCompany(companyId: number): void {
    this.isLoading.set(true);
    this.errorMessage.set(null);
    this.company.set(null);
    this.sites.set([]);
    this.siteEditorMode.set('list');
    this.editingSiteId.set(null);
    this.sitesError.set(null);
    this.sitesLoadedCompanyId.set(null);
    this.siteSubmitError.set(null);
    this.siteSubmitSuccess.set(null);

    this.companyService
      .getCompanyById(companyId)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (company) => {
          this.company.set(company);
          this.sites.set(this.sortSites(company.sites ?? []));
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

  private loadSites(companyId: number): void {
    this.sitesLoading.set(true);
    this.sitesError.set(null);

    this.siteService
      .listSites(companyId)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (sites) => {
          this.sites.set(this.sortSites(sites));
          this.sitesLoading.set(false);
          this.sitesLoadedCompanyId.set(companyId);
        },
        error: (error: HttpErrorResponse) => {
          this.sitesLoading.set(false);
          this.sites.set([]);
          this.sitesLoadedCompanyId.set(null);
          this.sitesError.set(
            error.error?.message ?? error.message ?? 'The sites endpoint could not be reached.',
          );
        },
      });
  }

  private loadSiteTypes(): void {
    this.siteTypesLoading.set(true);
    this.siteTypesError.set(null);

    this.siteService
      .getSiteTypeOptions()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (siteTypes) => {
          this.siteTypeOptions.set(siteTypes);
          this.siteTypesLoading.set(false);
        },
        error: (error: HttpErrorResponse) => {
          this.siteTypesLoading.set(false);
          this.siteTypesError.set(
            error.error?.message ?? error.message ?? 'The site types could not be loaded.',
          );
        },
      });
  }

  private loadSiteStatuses(): void {
    this.siteStatusesLoading.set(true);
    this.siteStatusesError.set(null);

    this.siteService
      .getSiteStatusOptions()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (siteStatuses) => {
          this.siteStatusOptions.set(siteStatuses);
          this.siteStatusesLoading.set(false);
        },
        error: (error: HttpErrorResponse) => {
          this.siteStatusesLoading.set(false);
          this.siteStatusesError.set(
            error.error?.message ?? error.message ?? 'The site statuses could not be loaded.',
          );
        },
      });
  }

  private ensureSitesLoaded(): void {
    if (this.sitesLoading()) {
      return;
    }

    if (this.sitesLoadedCompanyId() === this.companyId()) {
      return;
    }

    this.loadSites(this.companyId());
  }

  private ensureSiteTypesLoaded(): void {
    if (this.siteTypesLoading() || this.siteTypeOptions().length > 0) {
      return;
    }

    this.loadSiteTypes();
  }

  private ensureSiteStatusesLoaded(): void {
    if (this.siteStatusesLoading() || this.siteStatusOptions().length > 0) {
      return;
    }

    this.loadSiteStatuses();
  }

  private fallback(value?: string | null): string {
    return value?.trim() ? value : '—';
  }

  private optionalValue(value?: string | null): string | undefined {
    return value?.trim() ? value.trim() : undefined;
  }

  private sortSites(sites: SiteModel[]): SiteModel[] {
    return [...sites].sort((left, right) =>
      (left.siteName ?? '').localeCompare(right.siteName ?? '', 'en', { sensitivity: 'base' }),
    );
  }

  private upsertSite(sites: SiteModel[], site: SiteModel): SiteModel[] {
    const existingIndex = sites.findIndex((currentSite) => currentSite.id === site.id);

    if (existingIndex === -1) {
      return this.sortSites([site, ...sites]);
    }

    const updatedSites = [...sites];
    updatedSites[existingIndex] = site;
    return this.sortSites(updatedSites);
  }

  private resetSiteForm(): void {
    this.siteForm.reset({
      siteName: '',
      siteCode: '',
      city: '',
      country: 'AT',
      address: '',
      postalCode: '',
      siteType: '',
      status: 'ACTIVE',
      productionProcess: '',
      totalAreaM2: null,
      estimatedAnnualConsumptionTJ: null,
    });
  }

  private closeSiteForm(): void {
    this.siteEditorMode.set('list');
    this.editingSiteId.set(null);
    this.resetSiteForm();
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
