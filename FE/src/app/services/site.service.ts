import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, map } from 'rxjs';
import { CreateSiteRequest, SiteModel } from '../models/company.model';

@Injectable({
  providedIn: 'root',
})
export class SiteService {
  private readonly http = inject(HttpClient);
  private readonly apiBaseUrl = 'http://localhost:8080';
  private readonly openApiUrl = `${this.apiBaseUrl}/v3/api-docs`;

  listSites(companyId?: number): Observable<SiteModel[]> {
    const params = companyId ? new HttpParams().set('companyId', companyId) : undefined;
    return this.http.get<SiteModel[]>(`${this.apiBaseUrl}/sites`, { params });
  }

  createSite(payload: CreateSiteRequest): Observable<SiteModel> {
    return this.http.post<SiteModel>(`${this.apiBaseUrl}/sites`, payload);
  }

  updateSite(siteId: number, payload: CreateSiteRequest): Observable<SiteModel> {
    return this.http.put<SiteModel>(`${this.apiBaseUrl}/sites/${siteId}`, payload);
  }

  getSiteTypeOptions(): Observable<string[]> {
    return this.http.get<OpenApiDocument>(this.openApiUrl).pipe(
      map(
        (document) =>
          document.components?.schemas?.SiteEntityRequestBody?.properties?.siteType?.enum ?? [],
      ),
    );
  }

  getSiteStatusOptions(): Observable<string[]> {
    return this.http.get<OpenApiDocument>(this.openApiUrl).pipe(
      map(
        (document) =>
          document.components?.schemas?.SiteEntityRequestBody?.properties?.status?.enum ?? [],
      ),
    );
  }
}

interface OpenApiDocument {
  components?: {
    schemas?: {
      SiteEntityRequestBody?: {
        properties?: {
          siteType?: {
            enum?: string[];
          };
          status?: {
            enum?: string[];
          };
        };
      };
    };
  };
}
