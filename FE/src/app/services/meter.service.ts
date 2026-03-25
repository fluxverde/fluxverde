import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, map } from 'rxjs';
import { ManualMeterEntryModel, MeterReadingModel } from '../models/meter-data.model';
import { CreateMeterRequest, MeterModel, MeterTypeModel } from '../models/meter.model';

@Injectable({
  providedIn: 'root',
})
export class MeterService {
  private readonly http = inject(HttpClient);
  private readonly apiBaseUrl = 'http://localhost:8080';
  private readonly openApiUrl = `${this.apiBaseUrl}/v3/api-docs`;

  listMeters(): Observable<MeterModel[]> {
    return this.http.get<MeterModel[]>(`${this.apiBaseUrl}/meters`);
  }

  listMeterTypes(): Observable<MeterTypeModel[]> {
    return this.http.get<MeterTypeModel[]>(`${this.apiBaseUrl}/meter-types`);
  }

  listMeterReadings(): Observable<MeterReadingModel[]> {
    return this.http.get<MeterReadingModel[]>(`${this.apiBaseUrl}/meter-readings`);
  }

  listManualMeterEntries(): Observable<ManualMeterEntryModel[]> {
    return this.http.get<ManualMeterEntryModel[]>(`${this.apiBaseUrl}/manual-meter-entries`);
  }

  createMeter(payload: CreateMeterRequest): Observable<MeterModel> {
    return this.http.post<MeterModel>(`${this.apiBaseUrl}/meters`, payload);
  }

  updateMeter(meterId: number, payload: CreateMeterRequest): Observable<MeterModel> {
    return this.http.put<MeterModel>(`${this.apiBaseUrl}/meters/${meterId}`, payload);
  }

  getMeterCategoryOptions(): Observable<string[]> {
    return this.getMeterRequestBody().pipe(
      map((requestBody) => requestBody.properties?.meterCategory?.enum ?? []),
    );
  }

  getCollectionMethodOptions(): Observable<string[]> {
    return this.getMeterRequestBody().pipe(
      map((requestBody) => requestBody.properties?.collectionMethod?.enum ?? []),
    );
  }

  getReadingFrequencyOptions(): Observable<string[]> {
    return this.getMeterRequestBody().pipe(
      map((requestBody) => requestBody.properties?.readingFrequency?.enum ?? []),
    );
  }

  private getMeterRequestBody(): Observable<MeterRequestBodySchema> {
    return this.http.get<OpenApiDocument>(this.openApiUrl).pipe(
      map((document) => document.components?.schemas?.MeterEntityRequestBody ?? {}),
    );
  }
}

interface OpenApiDocument {
  components?: {
    schemas?: {
      MeterEntityRequestBody?: MeterRequestBodySchema;
    };
  };
}

interface MeterRequestBodySchema {
  properties?: {
    meterCategory?: { enum?: string[] };
    collectionMethod?: { enum?: string[] };
    readingFrequency?: { enum?: string[] };
  };
}
