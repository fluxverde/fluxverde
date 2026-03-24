import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { CompanyModel } from '../models/company.model';

@Injectable({
  providedIn: 'root',
})
export class CompanyService {
  private readonly http = inject(HttpClient);
  private readonly apiBaseUrl = 'http://localhost:8080';

  getCompanyById(companyId: number): Observable<CompanyModel> {
    return this.http.get<CompanyModel>(`${this.apiBaseUrl}/companies/${companyId}`);
  }
}
