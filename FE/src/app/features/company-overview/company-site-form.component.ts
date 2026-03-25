import { CommonModule } from '@angular/common';
import { Component, input, output } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-company-site-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  styleUrl: './company-site-form.component.scss',
  template: `
    <section
      class="rounded-[1.75rem] border border-emerald-900/10 bg-emerald-50/70 p-5 dark:border-emerald-100/10 dark:bg-slate-900/45"
      data-testid="site-form-view"
    >
      <div class="flex items-start justify-between gap-3">
        <div>
          <p class="text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
            {{ formEyebrow() }}
          </p>
          <h2 class="mt-2 text-2xl font-extrabold tracking-tight text-emerald-950 dark:text-emerald-50">
            {{ formTitle() }}
          </h2>
        </div>
        <div class="rounded-2xl border border-emerald-900/10 bg-white/80 px-3 py-2 text-right dark:border-emerald-100/10 dark:bg-slate-950/70">
          <p class="text-[11px] font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
            Company
          </p>
          <p class="mt-1 text-lg font-extrabold text-emerald-950 dark:text-emerald-50">
            {{ companyId() }}
          </p>
        </div>
      </div>

      <form class="mt-6 space-y-4" [formGroup]="form()" (ngSubmit)="submit.emit()" data-testid="site-form">
        <label class="block">
          <span class="mb-2 block text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
            Site name
          </span>
          <input type="text" formControlName="siteName" class="fv-input" data-testid="site-form-siteName" />
          @if (hasFieldError('siteName')) {
            <p class="fv-input-error">Site name is required.</p>
          }
        </label>

        <label class="block">
          <span class="mb-2 block text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
            Site code
          </span>
          <input type="text" formControlName="siteCode" class="fv-input" data-testid="site-form-siteCode" />
          @if (hasFieldError('siteCode')) {
            <p class="fv-input-error">Site code is required.</p>
          }
        </label>

        <div class="grid gap-4 sm:grid-cols-2">
          <label class="block">
            <span class="mb-2 block text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
              City
            </span>
            <input type="text" formControlName="city" class="fv-input" data-testid="site-form-city" />
          </label>

          <label class="block">
            <span class="mb-2 block text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
              Country
            </span>
            <input type="text" maxlength="2" formControlName="country" class="fv-input uppercase" data-testid="site-form-country" />
            @if (hasFieldError('country')) {
              <p class="fv-input-error">Use a two-letter country code.</p>
            }
          </label>
        </div>

        <label class="block">
          <span class="mb-2 block text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
            Address
          </span>
          <input type="text" formControlName="address" class="fv-input" data-testid="site-form-address" />
        </label>

        <div class="grid gap-4 sm:grid-cols-2">
          <label class="block">
            <span class="mb-2 block text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
              Postal code
            </span>
            <input type="text" formControlName="postalCode" class="fv-input" data-testid="site-form-postalCode" />
          </label>

          <label class="block">
            <span class="mb-2 block text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
              Site type
            </span>
            <select formControlName="siteType" class="fv-input" data-testid="site-form-siteType">
              <option value="">Select site type</option>
              @for (siteType of siteTypeOptions(); track siteType) {
                <option [value]="siteType">{{ formatOption(siteType) }}</option>
              }
            </select>
            @if (siteTypesLoading()) {
              <p class="fv-input-hint">Loading site types from backend...</p>
            } @else if (siteTypesError()) {
              <p class="fv-input-error">{{ siteTypesError() }}</p>
            }
          </label>
        </div>

        <div class="grid gap-4 sm:grid-cols-2">
          <label class="block">
            <span class="mb-2 block text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
              Status
            </span>
            <select formControlName="status" class="fv-input" data-testid="site-form-status">
              @for (status of siteStatusOptions(); track status) {
                <option [value]="status">{{ formatOption(status) }}</option>
              }
            </select>
            @if (siteStatusesLoading()) {
              <p class="fv-input-hint">Loading statuses from backend...</p>
            } @else if (siteStatusesError()) {
              <p class="fv-input-error">{{ siteStatusesError() }}</p>
            }
          </label>

          <label class="block">
            <span class="mb-2 block text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
              Total area (m²)
            </span>
            <input type="number" min="0" formControlName="totalAreaM2" class="fv-input" data-testid="site-form-totalAreaM2" />
          </label>

          <label class="block">
            <span class="mb-2 block text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
              Annual consumption (TJ)
            </span>
            <input type="number" min="0" step="0.01" formControlName="estimatedAnnualConsumptionTJ" class="fv-input" data-testid="site-form-estimatedAnnualConsumptionTJ" />
          </label>
        </div>

        <label class="block">
          <span class="mb-2 block text-xs font-semibold uppercase tracking-[0.16em] text-emerald-800/70 dark:text-emerald-100/70">
            Production process
          </span>
          <textarea rows="3" formControlName="productionProcess" class="fv-input fv-input--textarea" data-testid="site-form-productionProcess"></textarea>
        </label>

        @if (submitError()) {
          <div class="rounded-2xl border border-rose-300/70 bg-rose-50 px-4 py-3 text-sm leading-6 text-rose-950 dark:border-rose-300/20 dark:bg-rose-500/10 dark:text-rose-100">
            {{ submitError() }}
          </div>
        }

        <div class="flex flex-col gap-3 sm:flex-row sm:justify-end">
          <button
            type="button"
            (click)="cancel.emit()"
            data-testid="site-form-cancel"
            class="rounded-2xl border border-emerald-900/10 bg-white/85 px-5 py-3 text-sm font-semibold text-emerald-900 transition hover:bg-emerald-50 dark:border-emerald-100/10 dark:bg-slate-900/70 dark:text-emerald-50 dark:hover:bg-slate-900"
          >
            Cancel
          </button>
          <button
            type="submit"
            [disabled]="isSubmitting()"
            data-testid="site-form-submit"
            class="rounded-2xl bg-emerald-800 px-5 py-3 text-sm font-semibold text-white shadow-[0_14px_32px_-18px_rgba(6,95,70,.9)] transition hover:-translate-y-0.5 hover:bg-emerald-700 disabled:cursor-not-allowed disabled:opacity-60 dark:bg-emerald-500 dark:text-slate-950 dark:hover:bg-emerald-400"
          >
            {{ submitLabel() }}
          </button>
        </div>
      </form>
    </section>
  `,
})
export class CompanySiteFormComponent {
  readonly form = input.required<FormGroup>();
  readonly companyId = input.required<number>();
  readonly formEyebrow = input.required<string>();
  readonly formTitle = input.required<string>();
  readonly submitLabel = input.required<string>();
  readonly isSubmitting = input(false);
  readonly submitError = input<string | null>(null);
  readonly siteTypeOptions = input<string[]>([]);
  readonly siteTypesLoading = input(false);
  readonly siteTypesError = input<string | null>(null);
  readonly siteStatusOptions = input<string[]>([]);
  readonly siteStatusesLoading = input(false);
  readonly siteStatusesError = input<string | null>(null);
  readonly cancel = output<void>();
  readonly submit = output<void>();

  protected hasFieldError(controlName: string): boolean {
    const control = this.form().get(controlName);
    return !!control && control.invalid && (control.dirty || control.touched);
  }

  protected formatOption(value: string): string {
    return value
      .toLowerCase()
      .split('_')
      .map((segment) => segment.charAt(0).toUpperCase() + segment.slice(1))
      .join(' ');
  }
}
