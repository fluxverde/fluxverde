import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { catchError, forkJoin, map, Observable, of, switchMap } from 'rxjs';
import {
  CSVUploadModel,
  CsvUploadEntityCollection,
  MeterTypeModel,
} from '../models/csv-upload.model';
import { CompanyModel, SiteModel } from '../models/company.model';

@Injectable({
  providedIn: 'root',
})
export class CsvUploadService {
  private readonly http = inject(HttpClient);
  private readonly apiBaseUrl = 'http://localhost:8080';

  listCsvUploads(): Observable<CSVUploadModel[]> {
    return this.http
      .get<CsvUploadEntityCollection>(`${this.apiBaseUrl}/cSVUploadEntities`)
      .pipe(
        map((response) => response._embedded?.cSVUploadEntities ?? []),
        switchMap((uploads) => {
          if (!uploads.length) {
            return of([]);
          }

          return forkJoin(
            uploads.map((upload) =>
              forkJoin({
                site: this.fetchRelation<SiteModel>(upload._links?.site?.href),
                company: this.fetchRelation<CompanyModel>(upload._links?.company?.href),
                meterType: this.fetchRelation<MeterTypeModel>(upload._links?.meterType?.href),
              }).pipe(
                map(({ site, company, meterType }) => ({
                  ...upload,
                  site,
                  company,
                  meterType,
                })),
              ),
            ),
          );
        }),
      );
  }

  private fetchRelation<T>(href?: string): Observable<T | undefined> {
    if (!href) {
      return of(undefined);
    }

    return this.http.get<T>(href).pipe(catchError(() => of(undefined)));
  }
}
