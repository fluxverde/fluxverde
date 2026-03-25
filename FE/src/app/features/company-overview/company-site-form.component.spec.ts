import { TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { getByTestId } from '@testing-library/dom';
import { CompanySiteFormComponent } from './company-site-form.component';

describe('CompanySiteFormComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompanySiteFormComponent],
      providers: [FormBuilder],
    }).compileComponents();
  });

  it('renders the site form with prefilled values and emits cancel/submit', () => {
    const formBuilder = TestBed.inject(FormBuilder);
    const form = formBuilder.group({
      siteName: ['GreenPort Utrecht Sort Center'],
      siteCode: ['NL-UTR-SORT-03'],
      city: ['Utrecht'],
      country: ['NL'],
      address: ['Cargoweg 18'],
      postalCode: ['3542 AE'],
      siteType: ['WAREHOUSE'],
      status: ['ACTIVE'],
      productionProcess: ['Parcel sorting and overnight consolidation'],
      totalAreaM2: [18500],
      estimatedAnnualConsumptionTJ: [18.4],
    });

    const fixture = TestBed.createComponent(CompanySiteFormComponent);
    fixture.componentRef.setInput('form', form);
    fixture.componentRef.setInput('companyId', 15);
    fixture.componentRef.setInput('formEyebrow', 'Edit site');
    fixture.componentRef.setInput('formTitle', 'Edit company site');
    fixture.componentRef.setInput('submitLabel', 'Save changes');
    fixture.componentRef.setInput('isSubmitting', false);
    fixture.componentRef.setInput('submitError', null);
    fixture.componentRef.setInput('siteTypeOptions', ['WAREHOUSE', 'OFFICE']);
    fixture.componentRef.setInput('siteTypesLoading', false);
    fixture.componentRef.setInput('siteTypesError', null);
    fixture.componentRef.setInput('siteStatusOptions', ['ACTIVE', 'INACTIVE']);
    fixture.componentRef.setInput('siteStatusesLoading', false);
    fixture.componentRef.setInput('siteStatusesError', null);

    const cancelSpy = vi.fn();
    const submitSpy = vi.fn();
    fixture.componentInstance.cancel.subscribe(cancelSpy);
    fixture.componentInstance.submit.subscribe(submitSpy);

    fixture.detectChanges();

    const element = fixture.nativeElement as HTMLElement;
    expect(getByTestId(element, 'site-form-view')).toBeTruthy();
    expect((getByTestId(element, 'site-form-siteName') as HTMLInputElement).value).toBe(
      'GreenPort Utrecht Sort Center',
    );
    expect((getByTestId(element, 'site-form-siteCode') as HTMLInputElement).value).toBe(
      'NL-UTR-SORT-03',
    );
    expect((getByTestId(element, 'site-form-city') as HTMLInputElement).value).toBe('Utrecht');
    expect((getByTestId(element, 'site-form-country') as HTMLInputElement).value).toBe('NL');
    expect((getByTestId(element, 'site-form-address') as HTMLInputElement).value).toBe(
      'Cargoweg 18',
    );
    expect((getByTestId(element, 'site-form-postalCode') as HTMLInputElement).value).toBe(
      '3542 AE',
    );
    expect((getByTestId(element, 'site-form-siteType') as HTMLSelectElement).value).toBe(
      'WAREHOUSE',
    );
    expect((getByTestId(element, 'site-form-status') as HTMLSelectElement).value).toBe('ACTIVE');
    expect((getByTestId(element, 'site-form-totalAreaM2') as HTMLInputElement).value).toBe(
      '18500',
    );
    expect(
      (getByTestId(element, 'site-form-estimatedAnnualConsumptionTJ') as HTMLInputElement).value,
    ).toBe('18.4');

    (getByTestId(element, 'site-form-cancel') as HTMLButtonElement).click();
    fixture.detectChanges();
    expect(cancelSpy).toHaveBeenCalledTimes(1);

    (getByTestId(element, 'site-form-submit') as HTMLButtonElement).click();
    fixture.detectChanges();
    expect(submitSpy).toHaveBeenCalledTimes(1);
  });
});
