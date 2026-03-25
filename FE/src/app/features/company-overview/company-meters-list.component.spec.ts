import { TestBed } from '@angular/core/testing';
import { CompanyMetersListComponent } from './company-meters-list.component';
import { MeterModel } from '../../models/meter.model';
import { SiteModel } from '../../models/company.model';
import { getAllByTestId, getByTestId } from '@testing-library/dom';

describe('CompanyMetersListComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompanyMetersListComponent],
    }).compileComponents();
  });

  it('renders meter rows and emits actions', () => {
    const site: SiteModel = { id: 1, siteName: 'GreenPort Rotterdam Hub' };
    const meters: MeterModel[] = [
      {
        id: 1,
        meterName: 'Warehouse Main Electricity Feed',
        meterCode: 'MTR-NL-RTM-EL-101',
        meterType: { id: 1, typeName: 'Electricity Imported Energy' },
        meterCategory: 'MAIN',
        collectionMethod: 'DIGITAL_API',
        readingFrequency: 'DAILY',
      },
    ];

    const fixture = TestBed.createComponent(CompanyMetersListComponent);
    fixture.componentRef.setInput('site', site);
    fixture.componentRef.setInput('meters', meters);
    fixture.componentRef.setInput('loading', false);
    fixture.componentRef.setInput('errorMessage', null);
    fixture.componentRef.setInput('successMessage', null);

    const backSpy = vi.fn();
    const addSpy = vi.fn();
    const editSpy = vi.fn();
    const dataSpy = vi.fn();
    fixture.componentInstance.back.subscribe(backSpy);
    fixture.componentInstance.addMeter.subscribe(addSpy);
    fixture.componentInstance.editMeter.subscribe(editSpy);
    fixture.componentInstance.viewData.subscribe(dataSpy);

    fixture.detectChanges();

    const element = fixture.nativeElement as HTMLElement;
    expect(getByTestId(element, 'meters-list-view')).toBeTruthy();
    expect(getAllByTestId(element, 'meter-table-row')).toHaveLength(1);
    expect(getAllByTestId(element, 'meter-table-name').map((node) => node.textContent?.trim())).toEqual([
      'Warehouse Main Electricity Feed',
    ]);

    (getByTestId(element, 'meter-add-button') as HTMLButtonElement).click();
    expect(addSpy).toHaveBeenCalledTimes(1);

    (getByTestId(element, 'meter-data-button') as HTMLButtonElement).click();
    expect(dataSpy).toHaveBeenCalledWith(meters[0]);

    (getByTestId(element, 'meter-edit-button') as HTMLButtonElement).click();
    expect(editSpy).toHaveBeenCalledWith(meters[0]);
  });
});
