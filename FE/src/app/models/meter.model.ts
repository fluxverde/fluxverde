export interface MeterTypeModel {
  id?: number;
  typeName?: string;
  unit?: string;
  category?: string;
  conversionFactorToTJ?: number;
  description?: string;
}

export interface MeterModel {
  id?: number;
  meterName?: string;
  meterCode?: string;
  meterSerialNumber?: string;
  manufacturer?: string;
  installationDate?: string;
  nominalPower?: number;
  accuracy?: string;
  location?: string;
  meterCategory?: string;
  collectionMethod?: string;
  readingFrequency?: string;
  isActive?: boolean;
  createdAt?: string;
  updatedAt?: string;
  site?: { id?: number } | null;
  meterType?: MeterTypeModel | null;
}

export interface CreateMeterRequest {
  meterName: string;
  meterCode: string;
  meterSerialNumber?: string;
  manufacturer?: string;
  installationDate?: string;
  nominalPower?: number | null;
  accuracy?: string;
  location?: string;
  meterCategory?: string;
  collectionMethod?: string;
  readingFrequency?: string;
  isActive?: boolean;
  site: { id: number };
  meterType?: { id: number };
}
