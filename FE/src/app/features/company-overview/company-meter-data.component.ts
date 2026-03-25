import { CommonModule } from '@angular/common';
import { Component, input, output } from '@angular/core';
import { ManualMeterEntryModel, MeterReadingModel } from '../../models/meter-data.model';
import { MeterModel } from '../../models/meter.model';

@Component({
  selector: 'app-company-meter-data',
  standalone: true,
  imports: [CommonModule],
  styleUrl: './company-sites-list.component.scss',
  template: `
    <section class="rounded-[1.75rem] border border-white/70 bg-white/65 p-5 shadow-[0_18px_60px_-38px_rgba(15,118,110,.4)] dark:border-emerald-100/10 dark:bg-slate-950/35">
      <div class="flex flex-col gap-4 sm:flex-row sm:items-end sm:justify-between">
        <div>
          <button
            type="button"
            (click)="back.emit()"
            class="inline-flex items-center gap-2 rounded-full border border-emerald-900/10 bg-white/80 px-3 py-1.5 text-xs font-semibold uppercase tracking-[0.14em] text-emerald-900 transition hover:bg-emerald-50 dark:border-emerald-100/10 dark:bg-slate-900/70 dark:text-emerald-50 dark:hover:bg-slate-900"
          >
            <span aria-hidden="true">←</span>
            <span>Back to meters</span>
          </button>
          <p class="mt-4 text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
            Meter data
          </p>
          <h2 class="mt-2 text-2xl font-extrabold tracking-tight text-emerald-950 dark:text-emerald-50">
            {{ meter()?.meterName || 'Selected meter' }}
          </h2>
          <p class="mt-2 text-sm leading-6 text-emerald-950/70 dark:text-emerald-100/70">
            Automatic readings and manual entries for this meter.
          </p>
        </div>
      </div>

      <div class="mt-6 grid gap-6 xl:grid-cols-2">
        <section class="rounded-[1.5rem] border border-emerald-900/10 bg-white/70 p-4 dark:border-emerald-100/10 dark:bg-slate-950/45" data-testid="meter-readings-panel">
          <p class="text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">Automatic readings</p>
          @if (readingsError()) {
            <div class="mt-4 rounded-2xl border border-rose-300/60 bg-rose-50 px-4 py-4 text-sm leading-6 text-rose-950 dark:border-rose-300/20 dark:bg-rose-500/10 dark:text-rose-100">
              {{ readingsError() }}
            </div>
          } @else if (readings().length) {
            <div class="mt-4 space-y-3">
              @for (reading of readings(); track reading.id ?? $index) {
                <article class="rounded-2xl border border-emerald-900/10 bg-emerald-50/60 px-4 py-4 dark:border-emerald-100/10 dark:bg-slate-900/45" data-testid="meter-reading-row">
                  <p class="text-sm font-semibold text-emerald-950 dark:text-emerald-50">
                    {{ formatDateTime(reading.readingTimestamp) }} • {{ reading.readingValue ?? '—' }}
                  </p>
                  <p class="mt-1 text-xs uppercase tracking-[0.14em] text-emerald-800/65 dark:text-emerald-100/55">
                    {{ formatEnum(reading.source) }} • {{ formatEnum(reading.readingStatus) }}
                  </p>
                </article>
              }
            </div>
          } @else {
            <div class="mt-4 rounded-2xl border border-dashed border-emerald-900/15 bg-emerald-50/60 px-4 py-4 text-sm leading-6 text-emerald-950/75 dark:border-emerald-100/15 dark:bg-slate-900/40 dark:text-emerald-100/75">
              No automatic readings available yet.
            </div>
          }
        </section>

        <section class="rounded-[1.5rem] border border-emerald-900/10 bg-white/70 p-4 dark:border-emerald-100/10 dark:bg-slate-950/45" data-testid="manual-entries-panel">
          <p class="text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">Manual entries</p>
          @if (manualEntriesError()) {
            <div class="mt-4 rounded-2xl border border-rose-300/60 bg-rose-50 px-4 py-4 text-sm leading-6 text-rose-950 dark:border-rose-300/20 dark:bg-rose-500/10 dark:text-rose-100">
              {{ manualEntriesError() }}
            </div>
          } @else if (manualEntries().length) {
            <div class="mt-4 space-y-3">
              @for (entry of manualEntries(); track entry.id ?? $index) {
                <article class="rounded-2xl border border-emerald-900/10 bg-emerald-50/60 px-4 py-4 dark:border-emerald-100/10 dark:bg-slate-900/45" data-testid="manual-entry-row">
                  <p class="text-sm font-semibold text-emerald-950 dark:text-emerald-50">
                    {{ formatDate(entry.entryDate) }} • {{ entry.meterReadingValue ?? '—' }} {{ entry.unit || '' }}
                  </p>
                  <p class="mt-1 text-xs uppercase tracking-[0.14em] text-emerald-800/65 dark:text-emerald-100/55">
                    {{ entry.enteredBy || 'Unknown user' }} • {{ formatEnum(entry.verificationStatus) }}
                  </p>
                </article>
              }
            </div>
          } @else {
            <div class="mt-4 rounded-2xl border border-dashed border-emerald-900/15 bg-emerald-50/60 px-4 py-4 text-sm leading-6 text-emerald-950/75 dark:border-emerald-100/15 dark:bg-slate-900/40 dark:text-emerald-100/75">
              No manual entries available yet.
            </div>
          }
        </section>
      </div>
    </section>
  `,
})
export class CompanyMeterDataComponent {
  readonly meter = input<MeterModel | null>(null);
  readonly readings = input<MeterReadingModel[]>([]);
  readonly readingsError = input<string | null>(null);
  readonly manualEntries = input<ManualMeterEntryModel[]>([]);
  readonly manualEntriesError = input<string | null>(null);
  readonly back = output<void>();

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

  protected formatDate(value?: string | null): string {
    if (!value) {
      return '—';
    }
    return new Intl.DateTimeFormat('en-US', { dateStyle: 'medium' }).format(new Date(value));
  }

  protected formatDateTime(value?: string | null): string {
    if (!value) {
      return '—';
    }
    return new Intl.DateTimeFormat('en-US', { dateStyle: 'medium', timeStyle: 'short' }).format(
      new Date(value),
    );
  }
}
