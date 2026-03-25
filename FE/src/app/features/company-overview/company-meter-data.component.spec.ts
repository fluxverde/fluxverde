import { TestBed } from '@angular/core/testing';
import { getAllByTestId, getByTestId } from '@testing-library/dom';
import { CompanyMeterDataComponent } from './company-meter-data.component';

describe('CompanyMeterDataComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompanyMeterDataComponent],
    }).compileComponents();
  });

  it('renders automatic readings and manual entries panels', () => {
    const fixture = TestBed.createComponent(CompanyMeterDataComponent);
    fixture.componentRef.setInput('meter', { id: 1, meterName: 'Warehouse Main Electricity Feed' });
    fixture.componentRef.setInput('readings', [
      {
        id: 1,
        readingTimestamp: '2026-03-20T08:00:00Z',
        readingValue: 2450.5,
        source: 'API',
        readingStatus: 'VALID',
      },
    ]);
    fixture.componentRef.setInput('readingsError', null);
    fixture.componentRef.setInput('manualEntries', [
      {
        id: 1,
        entryDate: '2026-03-19',
        meterReadingValue: 2448.2,
        unit: 'KWH',
        enteredBy: 'Anna van der Berg',
        verificationStatus: 'VERIFIED',
      },
    ]);
    fixture.componentRef.setInput('manualEntriesError', null);

    fixture.detectChanges();

    const element = fixture.nativeElement as HTMLElement;
    expect(getByTestId(element, 'meter-readings-panel')).toBeTruthy();
    expect(getByTestId(element, 'manual-entries-panel')).toBeTruthy();
    expect(getAllByTestId(element, 'meter-reading-row')).toHaveLength(1);
    expect(getAllByTestId(element, 'manual-entry-row')).toHaveLength(1);
  });
});
