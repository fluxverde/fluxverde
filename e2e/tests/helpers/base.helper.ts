export const baseUrl = process.env.API_URL || 'http://localhost:8080';

export const delay = (ms: number) =>
  new Promise(resolve => setTimeout(resolve, ms));
