import { test, expect } from '@playwright/test';
import { baseUrl, delay } from './helpers/base.helper';

test.describe('Company API Endpoints', () => {
  test('CRUD /companies - create, getById, list, update, delete', async ({ request }) => {
    const unique = Date.now();
    const createPayload = {
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

    let companyId: number | undefined;

    try {
      await delay(200);
      const createResponse = await request.post(`${baseUrl}/companies`, {
        data: createPayload,
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
      expect(createdCompany.companyName).toBe(createPayload.companyName);
      expect(createdCompany.registrationNumber).toBe(createPayload.registrationNumber);

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
      if (companyId !== undefined) {
        await request.delete(`${baseUrl}/companies/${companyId}`);
      }
    }
  });
});
