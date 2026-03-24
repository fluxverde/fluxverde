#!/usr/bin/env bash

set -euo pipefail

API_BASE="${API_BASE:-http://localhost:8080}"
SUFFIX="${SUFFIX:-$(date +%s)}"

post() {
  local path="$1"
  local payload="$2"
  curl -s -X POST "${API_BASE}${path}" \
    -H 'Content-Type: application/json' \
    -d "${payload}"
}

extract_id() {
  jq -r '.id'
}

create() {
  local label="$1"
  local path="$2"
  local payload="$3"
  local response
  local id

  response="$(post "${path}" "${payload}")"
  id="$(printf '%s' "${response}" | jq -r '.id // empty')"

  if [[ -z "${id}" ]]; then
    echo "Failed to create ${label}" >&2
    printf '%s\n' "${response}" >&2
    exit 1
  fi

  echo "${id}"
}

echo "Seeding related sample data into ${API_BASE}"

template_id="$(
  create "template" "/regulatory-templates" '{
    "templateCode":"TEMP-NL-'${SUFFIX}'",
    "templateName":"Netherlands Energy Audit Template '${SUFFIX}'",
    "country":"NL",
    "regulatoryFramework":"EU_EED",
    "validFromDate":"2026-01-01",
    "reportFormat":"EN_16247_1_PDF",
    "submissionFormat":"PDF",
    "submissionAuthority":"RVO",
    "reportLanguage":"en",
    "version":"1.0",
    "status":"ACTIVE"
  }'
)"
echo "Template: ${template_id}"

company_id="$(
  create "company" "/companies" '{
    "companyName":"NorthSea Cold Chain BV '${SUFFIX}'",
    "registrationNumber":"NL-RTM-'${SUFFIX}'-CC",
    "country":"NL",
    "legalRepresentative":"Marta de Vries",
    "contactEmail":"energy@northseacoldchain.eu",
    "contactPhone":"+31 10 555 7800",
    "industry":"Cold Storage & Logistics",
    "employeeCount":310,
    "annualRevenueMeur":88.4,
    "totalEnergyTjPerYear":67.2,
    "regulatoryObligation":"BETWEEN_10_85_TJ",
    "nextMandatoryAuditDate":"2028-06-30",
    "lastAuditDate":"2025-06-30",
    "auditMethodology":"EN_16247_1",
    "status":"ACTIVE"
  }'
)"
echo "Company: ${company_id}"

site_id="$(
  create "site" "/sites" "{
    \"siteName\":\"NorthSea Cold Chain Terminal ${SUFFIX}\",
    \"siteCode\":\"NL-RTM-COLD-${SUFFIX}\",
    \"address\":\"Koelhuisweg 12\",
    \"city\":\"Rotterdam\",
    \"postalCode\":\"3089 KE\",
    \"country\":\"NL\",
    \"siteType\":\"WAREHOUSE\",
    \"productionProcess\":\"Refrigerated storage and dispatch\",
    \"totalAreaM2\":28500,
    \"estimatedAnnualConsumptionTJ\":67.2,
    \"estimatedAnnualConsumptionkWh\":18666667,
    \"status\":\"ACTIVE\",
    \"company\":{\"id\":${company_id}}
  }"
)"
echo "Site: ${site_id}"

company_user_id="$(
  create "company user" "/company-users" "{
    \"userEmail\":\"marta.${SUFFIX}@northseacoldchain.eu\",
    \"firstName\":\"Marta\",
    \"lastName\":\"de Vries\",
    \"userRole\":\"ADMIN\",
    \"phoneNumber\":\"+31 10 555 7801\",
    \"isActive\":true,
    \"company\":{\"id\":${company_id}}
  }"
)"
echo "Company user: ${company_user_id}"

benchmark_id="$(
  create "benchmark" "/energy-benchmarks" "{
    \"benchmarkCode\":\"BENCH-COLD-${SUFFIX}\",
    \"country\":\"NL\",
    \"industry\":\"Cold Storage & Logistics\",
    \"siteType\":\"WAREHOUSE\",
    \"typicalEnergyUsekWhM2\":520,
    \"medianEnergyUsekWhM2\":475,
    \"bestInClasskWhM2\":390,
    \"sourceDataYear\":2025,
    \"numberOfFacilitiesInSample\":120,
    \"description\":\"Warehouse refrigeration benchmark\",
    \"sourceReference\":\"EU Logistics Energy Study 2025\",
    \"company\":{\"id\":${company_id}}
  }"
)"
echo "Benchmark: ${benchmark_id}"

meter_type_power_id="$(
  create "electricity meter type" "/meter-types" '{
    "typeName":"Electricity '${SUFFIX}'",
    "unit":"KWH",
    "category":"ENERGY",
    "conversionFactorToTJ":0.0000036,
    "description":"Electrical energy import"
  }'
)"
meter_type_gas_id="$(
  create "gas meter type" "/meter-types" '{
    "typeName":"Natural Gas '${SUFFIX}'",
    "unit":"KWH",
    "category":"GAS",
    "conversionFactorToTJ":0.0000036,
    "description":"Gas energy import"
  }'
)"
echo "Meter types: ${meter_type_power_id}, ${meter_type_gas_id}"

meter_power_id="$(
  create "power meter" "/meters" "{
    \"meterName\":\"Main Grid Feed\",
    \"meterCode\":\"MTR-NL-POWER-${SUFFIX}\",
    \"meterSerialNumber\":\"SN-EL-${SUFFIX}\",
    \"manufacturer\":\"Siemens\",
    \"installationDate\":\"2024-01-15\",
    \"nominalPower\":2500,
    \"accuracy\":\"Class 0.5S\",
    \"location\":\"Main incomer room\",
    \"meterCategory\":\"MAIN\",
    \"collectionMethod\":\"CSV_IMPORT\",
    \"readingFrequency\":\"MONTHLY\",
    \"isActive\":true,
    \"site\":{\"id\":${site_id}},
    \"meterType\":{\"id\":${meter_type_power_id}}
  }"
)"
meter_gas_id="$(
  create "gas meter" "/meters" "{
    \"meterName\":\"Boiler Gas Feed\",
    \"meterCode\":\"MTR-NL-GAS-${SUFFIX}\",
    \"meterSerialNumber\":\"SN-GS-${SUFFIX}\",
    \"manufacturer\":\"Honeywell\",
    \"installationDate\":\"2024-01-15\",
    \"nominalPower\":1200,
    \"accuracy\":\"Class 1\",
    \"location\":\"Boiler room\",
    \"meterCategory\":\"SUB_METER\",
    \"collectionMethod\":\"CSV_IMPORT\",
    \"readingFrequency\":\"MONTHLY\",
    \"isActive\":true,
    \"site\":{\"id\":${site_id}},
    \"meterType\":{\"id\":${meter_type_gas_id}}
  }"
)"
echo "Meters: ${meter_power_id}, ${meter_gas_id}"

csv_upload_id="$(
  create "csv upload" "/c-s-v-uploads" "{
    \"fileName\":\"northsea-coldchain-q1-${SUFFIX}.csv\",
    \"fileSize\":182440,
    \"uploadedBy\":\"marta.devries@northseacoldchain.eu\",
    \"uploadTimestamp\":\"2026-03-24T08:40:00Z\",
    \"totalRecordsImported\":840,
    \"successfulRecords\":821,
    \"failedRecords\":11,
    \"skippedRecords\":8,
    \"importStatus\":\"PARTIAL_SUCCESS\",
    \"errorLog\":\"11 rows skipped because the meter code did not map to an active meter.\",
    \"isProcessed\":true,
    \"processedAt\":\"2026-03-24T08:44:18Z\",
    \"notes\":\"Quarterly refrigeration and boiler upload.\",
    \"site\":{\"id\":${site_id}},
    \"meterType\":{\"id\":${meter_type_power_id}},
    \"company\":{\"id\":${company_id}}
  }"
)"
echo "CSV upload: ${csv_upload_id}"

meter_reading_id="$(
  create "meter reading" "/meter-readings" "{
    \"readingTimestamp\":\"2026-03-01T00:00:00Z\",
    \"readingValue\":380000,
    \"readingStatus\":\"VALID\",
    \"confidence\":98,
    \"source\":\"CSV_IMPORT\",
    \"enteredByUser\":\"marta.devries@northseacoldchain.eu\",
    \"enteredAt\":\"2026-03-24T08:45:00Z\",
    \"isOutlier\":false,
    \"batchId\":2026032401,
    \"normalizedValue\":380000,
    \"meter\":{\"id\":${meter_power_id}},
    \"sourceUpload\":{\"id\":${csv_upload_id}}
  }"
)"
echo "Meter reading: ${meter_reading_id}"

manual_meter_entry_id="$(
  create "manual meter entry" "/manual-meter-entries" "{
    \"entryDate\":\"2026-03-05\",
    \"meterReadingValue\":41200,
    \"unit\":\"kWh\",
    \"enteredBy\":\"Marta de Vries\",
    \"entryTimestamp\":\"2026-03-05T09:15:00Z\",
    \"verificationStatus\":\"VERIFIED\",
    \"verifiedBy\":\"Marta de Vries\",
    \"verificationTimestamp\":\"2026-03-05T10:00:00Z\",
    \"notes\":\"Manual backfill for missed daily snapshot.\",
    \"meter\":{\"id\":${meter_gas_id}},
    \"enteredByUser\":{\"id\":${company_user_id}},
    \"verifiedByUser\":{\"id\":${company_user_id}}
  }"
)"
echo "Manual meter entry: ${manual_meter_entry_id}"

consumption_trend_id="$(
  create "consumption trend" "/consumption-trends" "{
    \"trendDate\":\"2026-03-01\",
    \"aggregationPeriod\":\"MONTHLY\",
    \"energyConsumptionkWh\":380000,
    \"energyConsumptionTJ\":1.368,
    \"specificEnergyUsekWhM2\":13.33,
    \"costPerUnit\":0.14,
    \"comparedToPreviousPeriod\":4.2,
    \"trend\":\"INCREASING\",
    \"ambientTemperatureC\":6.5,
    \"isClimateAdjusted\":true,
    \"site\":{\"id\":${site_id}},
    \"meter\":{\"id\":${meter_power_id}}
  }"
)"
echo "Consumption trend: ${consumption_trend_id}"

energy_audit_id="$(
  create "energy audit" "/energy-audits" "{
    \"auditCode\":\"AUD-NL-${SUFFIX}\",
    \"auditStartDate\":\"2026-04-01\",
    \"auditEndDate\":\"2026-04-15\",
    \"auditType\":\"MANDATORY\",
    \"leadAuditor\":\"Elena Vos\",
    \"supportingAuditors\":\"Marta de Vries\",
    \"referenceStartDate\":\"2025-01-01\",
    \"referenceEndDate\":\"2025-12-31\",
    \"auditStatus\":\"IN_PROGRESS\",
    \"regulatoryRequirement\":\"EED_10_85_TJ\",
    \"complianceFramework\":\"EN_16247_1\",
    \"totalEnergyConsumptionTJ\":67.2,
    \"totalEnergyConsumptionkWh\":18666667,
    \"estimatedAnnualSavingsTJ\":5.8,
    \"estimatedCostSavingsEUR\":420000,
    \"estimatedROIPercent\":24.5,
    \"reportGenerated\":false,
    \"site\":{\"id\":${site_id}}
  }"
)"
echo "Energy audit: ${energy_audit_id}"

audit_finding_id="$(
  create "audit finding" "/audit-findings" "{
    \"findingCode\":\"FIND-NL-${SUFFIX}\",
    \"findingTitle\":\"High compressor baseload outside production hours\",
    \"category\":\"CONTROL\",
    \"description\":\"Base electrical load remains elevated during non-operational hours, indicating compressor staging inefficiency.\",
    \"currentStatus\":\"OPEN\",
    \"recommendedAction\":\"Implement compressor sequencing and night setback controls.\",
    \"technicalParameters\":\"Night baseload 410 kW vs expected 290 kW.\",
    \"estimatedAnnualEnergySavingsTJ\":2.1,
    \"estimatedAnnualEnergySavingskWh\":583333,
    \"estimatedAnnualCostSavingsEUR\":102000,
    \"estimatedImplementationCostEUR\":140000,
    \"paybackPeriodYears\":1.37,
    \"estimatedROIPercent\":73,
    \"priority\":\"HIGH\",
    \"feasibility\":\"EASY\",
    \"implementationTimeline\":\"SHORT_TERM\",
    \"fundingAvailable\":\"NATIONAL_GRANT\",
    \"fundingSource\":\"National efficiency subsidy\",
    \"status\":\"IDENTIFIED\",
    \"affectedMeterCount\":2,
    \"audit\":{\"id\":${energy_audit_id}},
    \"primaryMeter\":{\"id\":${meter_power_id}},
    \"site\":{\"id\":${site_id}},
    \"affectedMeters\":[{\"id\":${meter_power_id}},{\"id\":${meter_gas_id}}]
  }"
)"
echo "Audit finding: ${audit_finding_id}"

audit_evidence_id="$(
  create "audit evidence" "/audit-evidences" "{
    \"evidenceCode\":\"EVID-NL-${SUFFIX}\",
    \"evidenceTitle\":\"Night load export\",
    \"fileName\":\"night-load-export-march-${SUFFIX}.xlsx\",
    \"fileType\":\"SPREADSHEET\",
    \"filePath\":\"/evidence/night-load-export-march-${SUFFIX}.xlsx\",
    \"fileSize\":38440,
    \"category\":\"OTHER\",
    \"uploadedBy\":\"Elena Vos\",
    \"uploadedAt\":\"2026-04-03T11:20:00Z\",
    \"description\":\"Hourly export demonstrating night baseload drift.\",
    \"audit\":{\"id\":${energy_audit_id}},
    \"finding\":{\"id\":${audit_finding_id}}
  }"
)"
echo "Audit evidence: ${audit_evidence_id}"

audit_checklist_id="$(
  create "audit checklist" "/audit-checklists" "{
    \"checklistItemCode\":\"CHK-NL-${SUFFIX}\",
    \"checklistItemTitle\":\"Confirm representative metering coverage\",
    \"category\":\"CONTROL_SYSTEMS\",
    \"isMandatory\":true,
    \"regulatoryReference\":\"EN 16247-1 Section 5\",
    \"description\":\"Verify that imported meter data covers representative operating periods.\",
    \"guidance\":\"Check monthly completeness and major process coverage.\",
    \"expectedDocumentation\":\"CSV upload reports and validation summary\",
    \"isCompleted\":false,
    \"comments\":\"Awaiting validation of freezer annex meter.\",
    \"audit\":{\"id\":${energy_audit_id}}
  }"
)"
echo "Audit checklist: ${audit_checklist_id}"

user_audit_access_id="$(
  create "user audit access" "/user-audit-accesses" "{
    \"accessLevel\":\"ADMIN\",
    \"grantedAt\":\"2026-04-01T07:00:00Z\",
    \"grantedBy\":\"System Seeder\",
    \"expiresAt\":\"2027-04-01T07:00:00Z\",
    \"user\":{\"id\":${company_user_id}},
    \"audit\":{\"id\":${energy_audit_id}}
  }"
)"
echo "User audit access: ${user_audit_access_id}"

report_generation_id="$(
  create "report generation" "/report-generations" "{
    \"reportCode\":\"REP-NL-${SUFFIX}\",
    \"reportType\":\"EN_16247_1\",
    \"reportFormat\":\"PDF\",
    \"generatedAt\":\"2026-04-15T16:00:00Z\",
    \"generatedBy\":\"Elena Vos\",
    \"reportTitle\":\"NorthSea Cold Chain Energy Audit Report\",
    \"reportFileName\":\"northsea-coldchain-audit-report-${SUFFIX}.pdf\",
    \"reportFilePath\":\"/reports/northsea-coldchain-audit-report-${SUFFIX}.pdf\",
    \"reportFileSize\":2421880,
    \"regulatoryTemplateUsed\":\"TEMP-NL-${SUFFIX}\",
    \"country\":\"NL\",
    \"includeFindings\":true,
    \"includeRecommendations\":true,
    \"includeCostAnalysis\":true,
    \"includeCharts\":true,
    \"confidentialityLevel\":\"INTERNAL\",
    \"reportStatus\":\"DRAFT\",
    \"version\":1,
    \"audit\":{\"id\":${energy_audit_id}}
  }"
)"
echo "Report generation: ${report_generation_id}"

export_log_id="$(
  create "export log" "/export-logs" "{
    \"exportCode\":\"EXP-NL-${SUFFIX}\",
    \"exportType\":\"PDF_REPORT\",
    \"exportFormat\":\"PDF\",
    \"exportedAt\":\"2026-04-15T16:05:00Z\",
    \"exportedBy\":\"Elena Vos\",
    \"exportedEntity\":\"AUDIT\",
    \"recordsExported\":1,
    \"exportFileName\":\"northsea-coldchain-audit-report-${SUFFIX}.pdf\",
    \"exportFilePath\":\"/exports/northsea-coldchain-audit-report-${SUFFIX}.pdf\",
    \"recipientEmail\":\"marta.${SUFFIX}@northseacoldchain.eu\",
    \"purpose\":\"MANAGEMENT_REVIEW\",
    \"audit\":{\"id\":${energy_audit_id}}
  }"
)"
echo "Export log: ${export_log_id}"

calculation_rule_id="$(
  create "calculation rule" "/calculation-rules" "{
    \"ruleCode\":\"RULE-NL-${SUFFIX}\",
    \"ruleName\":\"Cold storage baseload normalization\",
    \"country\":\"NL\",
    \"applicableToSiteType\":\"WAREHOUSE\",
    \"applicableToIndustry\":\"Cold Storage & Logistics\",
    \"formulaDescription\":\"Normalize annual energy use by conditioned storage area and average temperature lift.\",
    \"normalizationFactor\":\"CLIMATE_ADJUSTED\",
    \"benchmarkValue\":475,
    \"climateAdjustmentFactors\":\"Heating degree day correction = 0.84\",
    \"productionVolumeFactor\":1.1,
    \"calculationFormula\":\"Adjusted kWh / m2 = gross_kwh * HDD_factor / conditioned_area\",
    \"template\":{\"id\":${template_id}}
  }"
)"
echo "Calculation rule: ${calculation_rule_id}"

country_requirement_id="$(
  create "country requirement" "/country-requirements" "{
    \"requirementCode\":\"REQ-NL-${SUFFIX}\",
    \"requirementTitle\":\"Four-year mandatory audit cycle\",
    \"country\":\"NL\",
    \"regulatorySource\":\"EU_EED\",
    \"sourceReference\":\"EED Article 8\",
    \"description\":\"Large enterprises above the threshold must complete an energy audit every four years.\",
    \"applicableSiteType\":\"WAREHOUSE\",
    \"energyThresholdTJ\":10,
    \"implementationGuidance\":\"Retain auditable evidence for the prior reporting period.\",
    \"deadline\":\"2028-06-30\",
    \"referenceDocumentURL\":\"https://example.com/eed-article-8\",
    \"contactAuthority\":\"RVO\",
    \"template\":{\"id\":${template_id}}
  }"
)"
echo "Country requirement: ${country_requirement_id}"

echo "Done."
echo "Created ids:"
echo "  template=${template_id} company=${company_id} site=${site_id} user=${company_user_id}"
echo "  benchmark=${benchmark_id} meter_type_power=${meter_type_power_id} meter_type_gas=${meter_type_gas_id}"
echo "  meter_power=${meter_power_id} meter_gas=${meter_gas_id} csv_upload=${csv_upload_id}"
echo "  meter_reading=${meter_reading_id} manual_meter_entry=${manual_meter_entry_id} consumption_trend=${consumption_trend_id}"
echo "  audit=${energy_audit_id} finding=${audit_finding_id} evidence=${audit_evidence_id} checklist=${audit_checklist_id}"
echo "  access=${user_audit_access_id} report=${report_generation_id} export=${export_log_id}"
echo "  calculation_rule=${calculation_rule_id} country_requirement=${country_requirement_id}"
