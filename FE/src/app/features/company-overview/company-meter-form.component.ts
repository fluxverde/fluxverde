import { CommonModule } from '@angular/common';
import { Component, input, output } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { SiteModel } from '../../models/company.model';
import { MeterTypeModel } from '../../models/meter.model';

@Component({
  selector: 'app-company-meter-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  styleUrl: './company-site-form.component.scss',
  template: `
    <section class="rounded-[1.75rem] border border-emerald-900/10 bg-emerald-50/70 p-5 dark:border-emerald-100/10 dark:bg-slate-900/45" data-testid="meter-form-view">
      <div class="flex items-start justify-between gap-3">
        <div>
          <button
            type="button"
            (click)="cancel.emit()"
            class="inline-flex items-center gap-2 rounded-full border border-emerald-900/10 bg-white/80 px-3 py-1.5 text-xs font-semibold uppercase tracking-[0.14em] text-emerald-900 transition hover:bg-emerald-50 dark:border-emerald-100/10 dark:bg-slate-900/70 dark:text-emerald-50 dark:hover:bg-slate-900"
          >
            <span aria-hidden="true">←</span>
            <span>Back to meters</span>
          </button>
          <p class="mt-4 text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
            {{ formEyebrow() }}
          </p>
          <h2 class="mt-2 text-2xl font-extrabold tracking-tight text-emerald-950 dark:text-emerald-50">
            {{ formTitle() }}
          </h2>
          <p class="mt-2 text-sm leading-6 text-emerald-950/70 dark:text-emerald-100/70">
            Site: <span class="font-semibold">{{ site()?.siteName || 'Selected site' }}</span>
          </p>
        </div>
      </div>

      <form class="mt-6 space-y-4" [formGroup]="form()" (ngSubmit)="submit.emit()" data-testid="meter-form">
        <div class="grid gap-4 sm:grid-cols-2">
          <label class="block">
            <span class="mb-2 block text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
              Meter name
            </span>
            <input type="text" formControlName="meterName" class="fv-input" data-testid="meter-form-meterName" />
          </label>

          <label class="block">
            <span class="mb-2 block text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
              Meter code
            </span>
            <input type="text" formControlName="meterCode" class="fv-input" data-testid="meter-form-meterCode" />
          </label>
        </div>

        <div class="grid gap-4 sm:grid-cols-2">
          <label class="block">
            <span class="mb-2 block text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
              Serial number
            </span>
            <input type="text" formControlName="meterSerialNumber" class="fv-input" data-testid="meter-form-meterSerialNumber" />
          </label>

          <label class="block">
            <span class="mb-2 block text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
              Meter type
            </span>
            <select formControlName="meterTypeId" class="fv-input" data-testid="meter-form-meterTypeId">
              <option [ngValue]="null">Select meter type</option>
              @for (meterType of meterTypes(); track meterType.id) {
                <option [ngValue]="meterType.id">
                  {{ meterType.typeName }} @if (meterType.unit) { ({{ meterType.unit }}) }
                </option>
              }
            </select>
          </label>
        </div>

        <div class="grid gap-4 sm:grid-cols-2">
          <label class="block">
            <span class="mb-2 block text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
              Meter category
            </span>
            <select formControlName="meterCategory" class="fv-input" data-testid="meter-form-meterCategory">
              <option value="">Select category</option>
              @for (option of meterCategoryOptions(); track option) {
                <option [value]="option">{{ formatOption(option) }}</option>
              }
            </select>
          </label>

          <label class="block">
            <span class="mb-2 block text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
              Collection method
            </span>
            <select formControlName="collectionMethod" class="fv-input" data-testid="meter-form-collectionMethod">
              <option value="">Select collection method</option>
              @for (option of collectionMethodOptions(); track option) {
                <option [value]="option">{{ formatOption(option) }}</option>
              }
            </select>
          </label>
        </div>

        <div class="grid gap-4 sm:grid-cols-2">
          <label class="block">
            <span class="mb-2 block text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
              Reading frequency
            </span>
            <select formControlName="readingFrequency" class="fv-input" data-testid="meter-form-readingFrequency">
              <option value="">Select frequency</option>
              @for (option of readingFrequencyOptions(); track option) {
                <option [value]="option">{{ formatOption(option) }}</option>
              }
            </select>
          </label>

          <label class="block">
            <span class="mb-2 block text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
              Active
            </span>
            <select formControlName="isActive" class="fv-input" data-testid="meter-form-isActive">
              <option [ngValue]="true">Active</option>
              <option [ngValue]="false">Inactive</option>
            </select>
          </label>
        </div>

        <div class="grid gap-4 sm:grid-cols-2">
          <label class="block">
            <span class="mb-2 block text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
              Location
            </span>
            <input type="text" formControlName="location" class="fv-input" data-testid="meter-form-location" />
          </label>

          <label class="block">
            <span class="mb-2 block text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
              Manufacturer
            </span>
            <input type="text" formControlName="manufacturer" class="fv-input" data-testid="meter-form-manufacturer" />
          </label>
        </div>

        <div class="grid gap-4 sm:grid-cols-3">
          <label class="block">
            <span class="mb-2 block text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
              Installation date
            </span>
            <input type="date" formControlName="installationDate" class="fv-input" data-testid="meter-form-installationDate" />
          </label>

          <label class="block">
            <span class="mb-2 block text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
              Nominal power
            </span>
            <input type="number" min="0" formControlName="nominalPower" class="fv-input" data-testid="meter-form-nominalPower" />
          </label>

          <label class="block">
            <span class="mb-2 block text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
              Accuracy
            </span>
            <input type="text" formControlName="accuracy" class="fv-input" data-testid="meter-form-accuracy" />
          </label>
        </div>

        @if (submitError()) {
          <div class="rounded-2xl border border-rose-300/70 bg-rose-50 px-4 py-3 text-sm leading-6 text-rose-950 dark:border-rose-300/20 dark:bg-rose-500/10 dark:text-rose-100">
            {{ submitError() }}
          </div>
        }

        <div class="flex flex-col gap-3 sm:flex-row sm:justify-end">
          <button
            type="button"
            (click)="cancel.emit()"
            data-testid="meter-form-cancel"
            class="rounded-2xl border border-emerald-900/10 bg-white/85 px-5 py-3 text-sm font-semibold text-emerald-900 transition hover:bg-emerald-50 dark:border-emerald-100/10 dark:bg-slate-900/70 dark:text-emerald-50 dark:hover:bg-slate-900"
          >
            Cancel
          </button>
          <button
            type="submit"
            [disabled]="isSubmitting()"
            data-testid="meter-form-submit"
            class="rounded-2xl bg-emerald-800 px-5 py-3 text-sm font-semibold text-white shadow-[0_14px_32px_-18px_rgba(6,95,70,.9)] transition hover:-translate-y-0.5 hover:bg-emerald-700 disabled:cursor-not-allowed disabled:opacity-60 dark:bg-emerald-500 dark:text-slate-950 dark:hover:bg-emerald-400"
          >
            {{ submitLabel() }}
          </button>
        </div>
      </form>
    </section>
  `,
})
export class CompanyMeterFormComponent {
  readonly form = input.required<FormGroup>();
  readonly site = input<SiteModel | null>(null);
  readonly formEyebrow = input.required<string>();
  readonly formTitle = input.required<string>();
  readonly submitLabel = input.required<string>();
  readonly isSubmitting = input(false);
  readonly submitError = input<string | null>(null);
  readonly meterTypes = input<MeterTypeModel[]>([]);
  readonly meterCategoryOptions = input<string[]>([]);
  readonly collectionMethodOptions = input<string[]>([]);
  readonly readingFrequencyOptions = input<string[]>([]);
  readonly cancel = output<void>();
  readonly submit = output<void>();

  protected formatOption(value?: string | null): string {
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
