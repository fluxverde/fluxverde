import { MeterModel } from './meter.model';

export interface MeterReadingModel {
  id?: number;
  readingTimestamp?: string;
  readingValue?: number;
  readingStatus?: string;
  confidence?: number;
  source?: string;
  enteredByUser?: string;
  enteredAt?: string;
  isOutlier?: boolean;
  anomalyReason?: string;
  notes?: string;
  batchId?: number;
  normalizedValue?: number;
  meter?: MeterModel | null;
}

export interface ManualMeterEntryModel {
  id?: number;
  entryDate?: string;
  meterReadingValue?: number;
  unit?: string;
  enteredBy?: string;
  entryTimestamp?: string;
  verificationStatus?: string;
  verifiedBy?: string;
  verificationTimestamp?: string;
  notes?: string;
  correctionNotes?: string;
  sourceDocument?: string;
  meter?: MeterModel | null;
}
