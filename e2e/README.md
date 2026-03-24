# FluxVerde E2E Tests

This folder contains end-to-end tests for FluxVerde using Playwright.

## Prerequisites

- Node.js (18+ recommended)
- npm
- Backend running on `http://localhost:8080` (or set `API_URL`)

## Installation

```bash
npm install
```

If needed on Linux:

```bash
sudo npx playwright install-deps
```

## Running tests

Run all tests:

```bash
npm test
```

Run a single test file:

```bash
npx playwright test tests/api_company.spec.ts --reporter=list
```

Run against a different backend URL:

```bash
API_URL=http://localhost:8080 npm test
```

Run with UI mode:

```bash
npm run test:ui
```

Run in headed mode:

```bash
npm run test:headed
```

Run in debug mode:

```bash
npm run test:debug
```

Open HTML report:

```bash
npm run report
```

## Structure

- `tests/` test specs
- `tests/helpers/` shared helper utilities
- `playwright.config.ts` Playwright configuration
