import { test, expect } from '@playwright/test';
import { baseUrl, delay } from './helpers/base.helper';

test.describe('Company API Endpoints', () => {
  const buildCreatePayload = () => {
    const unique = Date.now();
    return {
      companyName: `E2E Company ${unique}`,
      registrationNumber: `REG-${unique}`,
      country: 'AT',
      legalRepresentative: 'E2E Tester',
      contactEmail: 'e2e.company@test.local',
      contactPhone: '+43123456789',
      industry: 'Software',
      employeeCount: 25,
      annualRevenueMeur: 2.1,
      totalEnergyTjPerYear: 0.7,
      regulatoryObligation: 'BETWEEN_10_85_TJ',
      auditMethodology: 'ISO_50001',
      status: 'ACTIVE'
    };
  };

  const createCompany = async (
    request: { post: (url: string, options?: Record<string, unknown>) => Promise<any> },
    payload: Record<string, unknown>
  ) => {
    await delay(200);
    const createResponse = await request.post(`${baseUrl}/companies`, {
      data: payload,
      headers: {
        'Content-Type': 'application/json'
      }
    });

    if (!createResponse.ok()) {
      console.log(await createResponse.text());
    }
    expect(createResponse.status()).toBe(201);
    expect(createResponse.headers()['content-type']).toContain('application/json');

    const createdCompany = await createResponse.json();
    expect(createdCompany).toBeDefined();
    expect(createdCompany.id).toBeDefined();

    return createdCompany;
  };

  const cleanupCompany = async (
    request: { delete: (url: string, options?: Record<string, unknown>) => Promise<any> },
    companyId?: number
  ) => {
    if (companyId === undefined) {
      return;
    }

    await request.delete(`${baseUrl}/companies/${companyId}`);
  };

  test('POST /companies - should create company', async ({ request }) => {
    const createPayload = buildCreatePayload();
    let companyId: number | undefined;

    try {
      const createdCompany = await createCompany(request, createPayload);
      companyId = createdCompany.id as number;

      expect(createdCompany.companyName).toBe(createPayload.companyName);
      expect(createdCompany.registrationNumber).toBe(createPayload.registrationNumber);
    } finally {
      await cleanupCompany(request, companyId);
    }
  });

  test('GET /companies/{id} - should get company by id', async ({ request }) => {
    const createPayload = buildCreatePayload();
    let companyId: number | undefined;

    try {
      const createdCompany = await createCompany(request, createPayload);
      companyId = createdCompany.id as number;

      await delay(200);
      const getByIdResponse = await request.get(`${baseUrl}/companies/${companyId}`);
      if (!getByIdResponse.ok()) {
        console.log(await getByIdResponse.text());
      }
      expect(getByIdResponse.status()).toBe(200);

      const fetchedCompany = await getByIdResponse.json();
      expect(fetchedCompany.id).toBe(companyId);
      expect(fetchedCompany.companyName).toBe(createPayload.companyName);
    } finally {
      await cleanupCompany(request, companyId);
    }
  });

  test('GET /companies - should list companies', async ({ request }) => {
    const createPayload = buildCreatePayload();
    let companyId: number | undefined;

    try {
      const createdCompany = await createCompany(request, createPayload);
      companyId = createdCompany.id as number;

      await delay(200);
      const listResponse = await request.get(`${baseUrl}/companies`);
      if (!listResponse.ok()) {
        console.log(await listResponse.text());
      }
      expect(listResponse.status()).toBe(200);
      expect(listResponse.headers()['content-type']).toContain('application/json');

      const companies = await listResponse.json();
      expect(Array.isArray(companies)).toBeTruthy();
      expect(companies.some((company: { id?: number }) => company.id === companyId)).toBeTruthy();
    } finally {
      await cleanupCompany(request, companyId);
    }
  });

  test('PUT /companies/{id} - should update company', async ({ request }) => {
    const createPayload = buildCreatePayload();
    let companyId: number | undefined;

    try {
      const createdCompany = await createCompany(request, createPayload);
      companyId = createdCompany.id as number;

      const updatePayload = {
        ...createPayload,
        companyName: `${createPayload.companyName} Updated`,
        registrationNumber: `${createPayload.registrationNumber}-U`
      };

      await delay(200);
      const updateResponse = await request.put(`${baseUrl}/companies/${companyId}`, {
        data: updatePayload,
        headers: {
          'Content-Type': 'application/json'
        }
      });
      if (!updateResponse.ok()) {
        console.log(await updateResponse.text());
      }
      expect(updateResponse.status()).toBe(200);

      const updatedCompany = await updateResponse.json();
      expect(updatedCompany.id).toBe(companyId);
      expect(updatedCompany.companyName).toBe(updatePayload.companyName);
      expect(updatedCompany.registrationNumber).toBe(updatePayload.registrationNumber);
    } finally {
      await cleanupCompany(request, companyId);
    }
  });

  test('DELETE /companies/{id} - should delete company', async ({ request }) => {
    const createPayload = buildCreatePayload();
    let companyId: number | undefined;

    try {
      const createdCompany = await createCompany(request, createPayload);
      companyId = createdCompany.id as number;

      await delay(200);
      const deleteResponse = await request.delete(`${baseUrl}/companies/${companyId}`);
      if (!deleteResponse.ok()) {
        console.log(await deleteResponse.text());
      }
      expect(deleteResponse.status()).toBe(204);

      await delay(200);
      const afterDeleteGetResponse = await request.get(`${baseUrl}/companies/${companyId}`);
      expect(afterDeleteGetResponse.status()).toBe(404);

      companyId = undefined;
    } finally {
      await cleanupCompany(request, companyId);
    }
  });
});
