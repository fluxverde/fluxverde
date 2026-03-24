import { CompanyModel, SiteModel } from './company.model';

export interface MeterTypeModel {
  id?: number;
  typeName?: string;
  unit?: string;
  category?: string;
  conversionFactorToTJ?: number;
  description?: string;
}

export interface CSVUploadModel {
  id?: number;
  fileName?: string;
  fileSize?: number;
  uploadedBy?: string;
  uploadTimestamp?: string;
  totalRecordsImported?: number;
  successfulRecords?: number;
  failedRecords?: number;
  skippedRecords?: number;
  importStatus?: string;
  errorLog?: string;
  isProcessed?: boolean;
  processedAt?: string;
  notes?: string;
  site?: SiteModel;
  meterType?: MeterTypeModel;
  company?: CompanyModel;
}
