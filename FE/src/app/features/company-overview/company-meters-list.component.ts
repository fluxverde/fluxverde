import { CommonModule } from '@angular/common';
import { Component, input, output } from '@angular/core';
import { SiteModel } from '../../models/company.model';
import { MeterModel } from '../../models/meter.model';

@Component({
  selector: 'app-company-meters-list',
  standalone: true,
  imports: [CommonModule],
  styleUrl: './company-sites-list.component.scss',
  template: `
    <section class="rounded-[1.75rem] border border-white/70 bg-white/65 p-5 shadow-[0_18px_60px_-38px_rgba(15,118,110,.4)] dark:border-emerald-100/10 dark:bg-slate-950/35" data-testid="meters-list-view">
      <div class="flex flex-col gap-4 sm:flex-row sm:items-end sm:justify-between">
        <div>
          <button
            type="button"
            (click)="back.emit()"
            class="inline-flex items-center gap-2 rounded-full border border-emerald-900/10 bg-white/80 px-3 py-1.5 text-xs font-semibold uppercase tracking-[0.14em] text-emerald-900 transition hover:bg-emerald-50 dark:border-emerald-100/10 dark:bg-slate-900/70 dark:text-emerald-50 dark:hover:bg-slate-900"
          >
            <span aria-hidden="true">←</span>
            <span>Back to sites</span>
          </button>
          <p class="mt-4 text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
            Site meters
          </p>
          <h2 class="mt-2 text-2xl font-extrabold tracking-tight text-emerald-950 dark:text-emerald-50">
            {{ site()?.siteName || 'Selected site' }}
          </h2>
          <p class="mt-2 text-sm leading-6 text-emerald-950/70 dark:text-emerald-100/70">
            Manage meters attached to this site before wiring automatic readings and manual entries.
          </p>
        </div>
        <div class="flex flex-wrap items-center gap-3">
          <div class="rounded-2xl border border-emerald-900/10 bg-emerald-50/80 px-4 py-3 text-right dark:border-emerald-100/10 dark:bg-emerald-900/20">
            <p class="text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">Meter count</p>
            <p class="mt-1 text-2xl font-extrabold text-emerald-950 dark:text-emerald-50">
              {{ meters().length }}
            </p>
          </div>
          <button
            type="button"
            (click)="addMeter.emit()"
            data-testid="meter-add-button"
            class="rounded-2xl bg-emerald-800 px-5 py-3 text-sm font-semibold text-white shadow-[0_14px_32px_-18px_rgba(6,95,70,.9)] transition hover:-translate-y-0.5 hover:bg-emerald-700 dark:bg-emerald-500 dark:text-slate-950 dark:hover:bg-emerald-400"
          >
            Add meter
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
      } @else if (meters().length) {
        <div class="mt-6 overflow-x-auto rounded-[1.5rem] border border-emerald-900/10 dark:border-emerald-100/10">
          <div class="site-table site-table--header">
            <span>Meter</span>
            <span>Code</span>
            <span>Type</span>
            <span>Category</span>
            <span>Collection</span>
            <span>Frequency</span>
            <span>Actions</span>
          </div>

          @for (meter of meters(); track trackMeter($index, meter)) {
            <div class="site-table" data-testid="meter-table-row">
              <div>
                <p class="font-semibold text-emerald-950 dark:text-emerald-50" data-testid="meter-table-name">{{ meter.meterName || 'Unnamed meter' }}</p>
                <p class="mt-1 text-xs text-emerald-800/65 dark:text-emerald-100/55">
                  {{ meter.location || 'No location' }}
                </p>
              </div>
              <span>{{ meter.meterCode || '—' }}</span>
              <span>{{ meter.meterType?.typeName || '—' }}</span>
              <span>{{ formatEnum(meter.meterCategory) }}</span>
              <span>{{ formatEnum(meter.collectionMethod) }}</span>
              <span>{{ formatEnum(meter.readingFrequency) }}</span>
              <div class="flex flex-wrap gap-2">
                <button
                  type="button"
                  (click)="viewData.emit(meter)"
                  data-testid="meter-data-button"
                  class="inline-flex items-center gap-2 rounded-xl border border-emerald-900/10 bg-white/85 px-3 py-2 text-sm font-semibold text-emerald-900 transition hover:bg-emerald-50 dark:border-emerald-100/10 dark:bg-slate-900/70 dark:text-emerald-50 dark:hover:bg-slate-900"
                >
                  <span aria-hidden="true">↗</span>
                  <span>Data</span>
                </button>
                <button
                  type="button"
                  (click)="editMeter.emit(meter)"
                  data-testid="meter-edit-button"
                  class="inline-flex items-center gap-2 rounded-xl border border-emerald-900/10 bg-white/85 px-3 py-2 text-sm font-semibold text-emerald-900 transition hover:bg-emerald-50 dark:border-emerald-100/10 dark:bg-slate-900/70 dark:text-emerald-50 dark:hover:bg-slate-900"
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
          No meters are attached to this site yet. Use Add meter to create the first one.
        </div>
      }
    </section>
  `,
})
export class CompanyMetersListComponent {
  readonly site = input<SiteModel | null>(null);
  readonly meters = input<MeterModel[]>([]);
  readonly loading = input(false);
  readonly errorMessage = input<string | null>(null);
  readonly successMessage = input<string | null>(null);
  readonly back = output<void>();
  readonly addMeter = output<void>();
  readonly editMeter = output<MeterModel>();
  readonly viewData = output<MeterModel>();

  protected trackMeter(index: number, meter: MeterModel): string | number {
    return meter.id ?? meter.meterCode ?? meter.meterName ?? index;
  }

  protected formatEnum(value?: string | null): string {
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
