import { Routes } from '@angular/router';
import { CompanyOverviewComponent } from './features/company-overview/company-overview.component';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'cases/CASE-005/overview',
  },
  {
    path: 'cases/:caseId/:section',
    component: CompanyOverviewComponent,
  },
  {
    path: '**',
    redirectTo: 'cases/CASE-005/overview',
  },
];
