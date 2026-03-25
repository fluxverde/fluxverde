import { TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { getByTestId } from '@testing-library/dom';
import { CompanyMeterFormComponent } from './company-meter-form.component';

describe('CompanyMeterFormComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompanyMeterFormComponent],
      providers: [FormBuilder],
    }).compileComponents();
  });

  it('renders prefilled meter form and emits cancel/submit', () => {
    const formBuilder = TestBed.inject(FormBuilder);
    const form = formBuilder.group({
      meterName: ['Warehouse Main Electricity Feed'],
      meterCode: ['MTR-NL-RTM-EL-101'],
      meterSerialNumber: ['SN-RTM-EL-101'],
      manufacturer: ['Siemens'],
      installationDate: ['2024-02-01'],
      nominalPower: [1800],
      accuracy: ['Class 0.5S'],
      location: ['Main electrical room'],
      meterCategory: ['MAIN'],
      collectionMethod: ['DIGITAL_API'],
      readingFrequency: ['DAILY'],
      isActive: [true],
      meterTypeId: [1],
    });

    const fixture = TestBed.createComponent(CompanyMeterFormComponent);
    fixture.componentRef.setInput('form', form);
    fixture.componentRef.setInput('site', { id: 1, siteName: 'GreenPort Rotterdam Hub' });
    fixture.componentRef.setInput('formEyebrow', 'Edit meter');
    fixture.componentRef.setInput('formTitle', 'Edit meter');
    fixture.componentRef.setInput('submitLabel', 'Save changes');
    fixture.componentRef.setInput('isSubmitting', false);
    fixture.componentRef.setInput('submitError', null);
    fixture.componentRef.setInput('meterTypes', [{ id: 1, typeName: 'Electricity Imported Energy' }]);
    fixture.componentRef.setInput('meterCategoryOptions', ['MAIN', 'SUB_METER']);
    fixture.componentRef.setInput('collectionMethodOptions', ['DIGITAL_API']);
    fixture.componentRef.setInput('readingFrequencyOptions', ['DAILY']);

    const cancelSpy = vi.fn();
    const submitSpy = vi.fn();
    fixture.componentInstance.cancel.subscribe(cancelSpy);
    fixture.componentInstance.submit.subscribe(submitSpy);

    fixture.detectChanges();

    const element = fixture.nativeElement as HTMLElement;
    expect(getByTestId(element, 'meter-form-view')).toBeTruthy();
    expect((getByTestId(element, 'meter-form-meterName') as HTMLInputElement).value).toBe(
      'Warehouse Main Electricity Feed',
    );
    expect((getByTestId(element, 'meter-form-meterCode') as HTMLInputElement).value).toBe(
      'MTR-NL-RTM-EL-101',
    );

    (getByTestId(element, 'meter-form-cancel') as HTMLButtonElement).click();
    expect(cancelSpy).toHaveBeenCalled();
    (getByTestId(element, 'meter-form-submit') as HTMLButtonElement).click();
    expect(submitSpy).toHaveBeenCalled();
  });
});
