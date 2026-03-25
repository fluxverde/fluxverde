import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { CreateSiteRequest, SiteModel } from '../models/company.model';

@Injectable({
  providedIn: 'root',
})
export class SiteService {
  private readonly http = inject(HttpClient);
  private readonly apiBaseUrl = 'http://localhost:8080';

  listSites(companyId?: number): Observable<SiteModel[]> {
    const params = companyId ? new HttpParams().set('companyId', companyId) : undefined;
    return this.http.get<SiteModel[]>(`${this.apiBaseUrl}/sites`, { params });
  }

  createSite(payload: CreateSiteRequest): Observable<SiteModel> {
    return this.http.post<SiteModel>(`${this.apiBaseUrl}/sites`, payload);
  }
}
