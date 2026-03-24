import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { CSVUploadModel } from '../models/csv-upload.model';

@Injectable({
  providedIn: 'root',
})
export class CsvUploadService {
  private readonly http = inject(HttpClient);
  private readonly apiBaseUrl = 'http://localhost:8080';

  listCsvUploads(): Observable<CSVUploadModel[]> {
    return this.http.get<CSVUploadModel[]>(`${this.apiBaseUrl}/c-s-v-uploads`);
  }
}
