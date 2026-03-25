import { CommonModule } from '@angular/common';
import { Component, input, output } from '@angular/core';
import { SiteModel } from '../../models/company.model';

@Component({
  selector: 'app-company-sites-list',
  standalone: true,
  imports: [CommonModule],
  styleUrl: './company-sites-list.component.scss',
  template: `
    <section
      class="rounded-[1.75rem] border border-white/70 bg-white/65 p-5 shadow-[0_18px_60px_-38px_rgba(15,118,110,.4)] dark:border-emerald-100/10 dark:bg-slate-950/35"
      data-testid="sites-list-view"
    >
      <div class="flex flex-col gap-3 sm:flex-row sm:items-end sm:justify-between">
        <div>
          <p class="text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
            Company sites
          </p>
          <h2 class="mt-2 text-2xl font-extrabold tracking-tight text-emerald-950 dark:text-emerald-50">
            Sites linked to this company
          </h2>
          <p class="mt-2 text-sm leading-6 text-emerald-950/70 dark:text-emerald-100/70">
            Records are loaded from the dedicated sites endpoint for the selected company.
          </p>
        </div>
        <div class="flex flex-wrap items-center gap-3">
          <div class="rounded-2xl border border-emerald-900/10 bg-emerald-50/80 px-4 py-3 text-right dark:border-emerald-100/10 dark:bg-emerald-900/20">
            <p class="text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">Site count</p>
            <p class="mt-1 text-2xl font-extrabold text-emerald-950 dark:text-emerald-50">
              {{ sites().length }}
            </p>
          </div>
          <button
            type="button"
            (click)="addSite.emit()"
            data-testid="site-add-button"
            class="rounded-2xl bg-emerald-800 px-5 py-3 text-sm font-semibold text-white shadow-[0_14px_32px_-18px_rgba(6,95,70,.9)] transition hover:-translate-y-0.5 hover:bg-emerald-700 dark:bg-emerald-500 dark:text-slate-950 dark:hover:bg-emerald-400"
          >
            Add site
          </button>
        </div>
      </div>

      @if (successMessage()) {
        <div class="mt-6 rounded-2xl border border-emerald-300/70 bg-emerald-50 px-4 py-3 text-sm leading-6 text-emerald-950 dark:border-emerald-300/20 dark:bg-emerald-500/10 dark:text-emerald-100">
          {{ successMessage() }}
        </div>
      }

      @if (loading()) {
        <div class="mt-6 grid gap-3">
          @for (item of [1, 2, 3]; track item) {
            <div class="h-24 rounded-[1.5rem] border border-emerald-900/10 bg-emerald-50/60 dark:border-emerald-100/10 dark:bg-slate-900/45"></div>
          }
        </div>
      } @else if (errorMessage()) {
        <div class="mt-6 rounded-[1.75rem] border border-rose-300/60 bg-rose-50 px-5 py-5 text-sm leading-6 text-rose-950 dark:border-rose-300/20 dark:bg-rose-500/10 dark:text-rose-100">
          {{ errorMessage() }}
        </div>
      } @else if (sites().length) {
        <div class="mt-6 overflow-x-auto rounded-[1.5rem] border border-emerald-900/10 dark:border-emerald-100/10" data-testid="sites-table">
          <div class="site-table site-table--header" data-testid="sites-table-header">
            <span>Site</span>
            <span>Location</span>
            <span>Type</span>
            <span>Status</span>
            <span>Area</span>
            <span>Energy</span>
            <span>Actions</span>
          </div>

          @for (site of sites(); track trackSite($index, site)) {
            <div class="site-table" data-testid="site-table-row">
              <div data-testid="site-table-name-cell">
                <p class="font-semibold text-emerald-950 dark:text-emerald-50" data-testid="site-table-site-name">
                  {{ site.siteName || 'Unnamed site' }}
                </p>
                <p class="mt-1 text-xs uppercase tracking-[0.14em] text-emerald-800/65 dark:text-emerald-100/55" data-testid="site-table-site-code">
                  {{ site.siteCode || 'No code' }}
                </p>
              </div>
              <span data-testid="site-table-location">{{ formatSiteLocation(site) }}</span>
              <span data-testid="site-table-type">{{ site.siteType || '—' }}</span>
              <span data-testid="site-table-status">{{ site.status || '—' }}</span>
              <span data-testid="site-table-area">{{ site.totalAreaM2 ? site.totalAreaM2 + ' m²' : '—' }}</span>
              <span data-testid="site-table-energy">{{ site.estimatedAnnualConsumptionTJ ? site.estimatedAnnualConsumptionTJ + ' TJ' : '—' }}</span>
              <div class="flex flex-wrap gap-2">
                <button
                  type="button"
                  (click)="viewMeters.emit(site)"
                  class="inline-flex items-center gap-2 rounded-xl border border-emerald-900/10 bg-white/85 px-3 py-2 text-sm font-semibold text-emerald-900 transition hover:bg-emerald-50 dark:border-emerald-100/10 dark:bg-slate-900/70 dark:text-emerald-50 dark:hover:bg-slate-900"
                >
                  <span aria-hidden="true">≡</span>
                  <span>Meters</span>
                </button>
                <button
                  type="button"
                  (click)="editSite.emit(site)"
                  data-testid="site-edit-button"
                  class="inline-flex items-center gap-2 rounded-xl border border-emerald-900/10 bg-white/85 px-3 py-2 text-sm font-semibold text-emerald-900 transition hover:bg-emerald-50 dark:border-emerald-100/10 dark:bg-slate-900/70 dark:text-emerald-50 dark:hover:bg-slate-900"
                  aria-label="Edit site"
                >
                  <span aria-hidden="true">✎</span>
                  <span>Edit</span>
                </button>
              </div>
            </div>
          }
        </div>
      } @else {
        <div class="mt-6 rounded-[1.75rem] border border-dashed border-emerald-900/15 bg-emerald-50/60 px-5 py-8 text-sm leading-6 text-emerald-950/75 dark:border-emerald-100/15 dark:bg-slate-900/40 dark:text-emerald-100/75">
          No sites are attached to this company yet. Use Add site to create the first one.
        </div>
      }
    </section>
  `,
})
export class CompanySitesListComponent {
  readonly sites = input<SiteModel[]>([]);
  readonly loading = input(false);
  readonly errorMessage = input<string | null>(null);
  readonly successMessage = input<string | null>(null);
  readonly addSite = output<void>();
  readonly editSite = output<SiteModel>();
  readonly viewMeters = output<SiteModel>();

  protected formatSiteLocation(site: SiteModel): string {
    return [site.city, site.country].filter(Boolean).join(', ') || '—';
  }

  protected trackSite(index: number, site: SiteModel): string | number {
    return site.id ?? site.siteCode ?? site.siteName ?? index;
  }
}
