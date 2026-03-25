import { TestBed } from '@angular/core/testing';
import { getAllByTestId, getByTestId } from '@testing-library/dom';
import { CompanySitesListComponent } from './company-sites-list.component';
import { SiteModel } from '../../models/company.model';

describe('CompanySitesListComponent', () => {
  const mockSites: SiteModel[] = [
    {
      id: 1,
      siteName: 'GreenPort Rotterdam Hub',
      siteCode: 'NL-RTM-HUB-01',
      city: 'Rotterdam',
      country: 'NL',
      siteType: 'WAREHOUSE',
      status: 'ACTIVE',
      totalAreaM2: 62000,
      estimatedAnnualConsumptionTJ: 96.2,
    },
    {
      id: 2,
      siteName: 'NorthSea Cold Chain Terminal B',
      siteCode: 'NL-RTM-COLD-02',
      city: 'Rotterdam',
      country: 'NL',
      siteType: 'WAREHOUSE',
      status: 'ACTIVE',
      totalAreaM2: 22500,
      estimatedAnnualConsumptionTJ: 41.1,
    },
  ];

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompanySitesListComponent],
    }).compileComponents();
  });

  it('renders the sites table and emits add/edit actions', () => {
    const fixture = TestBed.createComponent(CompanySitesListComponent);
    fixture.componentRef.setInput('sites', mockSites);
    fixture.componentRef.setInput('loading', false);
    fixture.componentRef.setInput('errorMessage', null);
    fixture.componentRef.setInput('successMessage', null);

    const addSiteSpy = vi.fn();
    const editSiteSpy = vi.fn();
    fixture.componentInstance.addSite.subscribe(addSiteSpy);
    fixture.componentInstance.editSite.subscribe(editSiteSpy);

    fixture.detectChanges();

    const element = fixture.nativeElement as HTMLElement;
    expect(getByTestId(element, 'sites-list-view')).toBeTruthy();
    expect(getByTestId(element, 'sites-table')).toBeTruthy();

    const rows = getAllByTestId(element, 'site-table-row');
    expect(rows).toHaveLength(2);

    const names = getAllByTestId(element, 'site-table-site-name').map((node) =>
      node.textContent?.trim(),
    );
    expect(names).toEqual([
      'GreenPort Rotterdam Hub',
      'NorthSea Cold Chain Terminal B',
    ]);

    const codes = getAllByTestId(element, 'site-table-site-code').map((node) =>
      node.textContent?.trim(),
    );
    expect(codes).toEqual(['NL-RTM-HUB-01', 'NL-RTM-COLD-02']);

    const locations = getAllByTestId(element, 'site-table-location').map((node) =>
      node.textContent?.trim(),
    );
    expect(locations).toEqual(['Rotterdam, NL', 'Rotterdam, NL']);

    (getByTestId(element, 'site-add-button') as HTMLButtonElement).click();
    fixture.detectChanges();
    expect(addSiteSpy).toHaveBeenCalledTimes(1);

    const editButtons = getAllByTestId(element, 'site-edit-button');
    (editButtons[0] as HTMLButtonElement).click();
    fixture.detectChanges();
    expect(editSiteSpy).toHaveBeenCalledWith(mockSites[0]);
  });
});
