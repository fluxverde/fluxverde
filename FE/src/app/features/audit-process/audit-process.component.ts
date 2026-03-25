import { CommonModule, DatePipe } from '@angular/common';
import { Component, computed, input } from '@angular/core';
import { CompanyModel } from '../../models/company.model';
import { CSVUploadModel } from '../../models/csv-upload.model';

type StepStatus = 'done' | 'current' | 'upcoming' | 'skipped';

interface ProcessContext {
  company: CompanyModel | null;
  uploads: CSVUploadModel[];
}

interface AuditStepViewModel {
  title: string;
  description: string;
  signal: string;
  status: StepStatus;
}

interface AuditPhaseViewModel {
  id: string;
  title: string;
  summary: string;
  status: Exclude<StepStatus, 'skipped'>;
  completedSteps: number;
  totalSteps: number;
  steps: AuditStepViewModel[];
}

@Component({
  selector: 'app-audit-process',
  standalone: true,
  imports: [CommonModule, DatePipe],
  templateUrl: './audit-process.component.html',
  styleUrl: './audit-process.component.scss',
})
export class AuditProcessComponent {
  readonly company = input<CompanyModel | null>(null);
  readonly uploads = input<CSVUploadModel[]>([]);
  readonly companyId = input<number>(1);
  readonly caseId = input('CASE-005');
  readonly loadingEvidence = input(false);
  readonly evidenceError = input<string | null>(null);

  protected readonly processData = computed(() => this.buildProcessData(this.company(), this.uploads()));

  protected readonly progressPercent = computed(() => {
    const { phases } = this.processData();
    const done = phases.reduce((count, phase) => count + phase.completedSteps, 0);
    const total = phases.reduce((count, phase) => count + phase.totalSteps, 0);

    return total ? Math.round((done / total) * 100) : 0;
  });

  protected readonly currentPhase = computed(() => {
    return this.processData().phases.find((phase) => phase.status === 'current') ?? null;
  });

  protected readonly nextMilestone = computed(() => {
    const currentPhase = this.currentPhase();

    return currentPhase?.steps.find((step) => step.status === 'current') ?? null;
  });

  protected readonly completedPhaseCount = computed(
    () => this.processData().phases.filter((phase) => phase.status === 'done').length,
  );

  protected readonly processedUploadCount = computed(
    () => this.processData().uploadsInScope.filter((upload) => upload.isProcessed).length,
  );

  protected phaseStatusClass(status: Exclude<StepStatus, 'skipped'>): string {
    switch (status) {
      case 'done':
        return 'fv-audit-process__badge fv-audit-process__badge--done';
      case 'current':
        return 'fv-audit-process__badge fv-audit-process__badge--current';
      default:
        return 'fv-audit-process__badge fv-audit-process__badge--upcoming';
    }
  }

  protected stepStatusClass(status: StepStatus): string {
    switch (status) {
      case 'done':
        return 'fv-audit-process__step-dot fv-audit-process__step-dot--done';
      case 'current':
        return 'fv-audit-process__step-dot fv-audit-process__step-dot--current';
      case 'skipped':
        return 'fv-audit-process__step-dot fv-audit-process__step-dot--skipped';
      default:
        return 'fv-audit-process__step-dot fv-audit-process__step-dot--upcoming';
    }
  }

  protected stepStatusLabel(status: StepStatus): string {
    switch (status) {
      case 'done':
        return 'Complete';
      case 'current':
        return 'In progress';
      case 'skipped':
        return 'Not required';
      default:
        return 'Queued';
    }
  }

  private buildProcessData(company: CompanyModel | null, uploads: CSVUploadModel[]) {
    const uploadsInScope = this.resolveUploadsInScope(company, uploads);
    const processedUploads = uploadsInScope.filter((upload) => upload.isProcessed);
    const successfulUploads = processedUploads.filter(
      (upload) => upload.importStatus === 'SUCCESS' || upload.importStatus === 'PARTIAL_SUCCESS',
    );
    const uploadedNotes = processedUploads.filter((upload) => Boolean(upload.notes?.trim()));
    const hasEligibilitySignals = Boolean(
      company?.regulatoryObligation ||
        company?.employeeCount ||
        company?.annualRevenueMeur ||
        company?.totalEnergyTjPerYear ||
        company?.nextMandatoryAuditDate,
    );
    const hasTeam = (company?.users?.length ?? 0) > 0;
    const hasContacts = Boolean(
      company?.contactEmail || company?.contactPhone || company?.legalRepresentative,
    );
    const hasSites = (company?.sites?.length ?? 0) > 0;
    const hasBaselineData = Boolean(
      company?.totalEnergyTjPerYear ||
        company?.sites?.some((site) => Boolean(site.estimatedAnnualConsumptionTJ)),
    );
    const hasEnergyData = hasBaselineData || uploadsInScope.length > 0;
    const hasExecutionEvidence = processedUploads.length > 0;
    const hasChecklistSignals = successfulUploads.length > 0 || uploadedNotes.length > 0;
    const hasAnalysisSignals =
      successfulUploads.length > 0 || (company?.benchmarks?.length ?? 0) > 0;
    const hasClosedAudit = Boolean(company?.lastAuditDate);
    const needsMandatoryFiling = this.needsMandatoryFiling(company);
    const hasSubmissionSignal = /submitted|filed|reported|closed/.test(
      (company?.status ?? '').toLowerCase(),
    );

    const stepDefinitions = [
      {
        phaseId: 'initiation',
        phaseTitle: 'Phase 0: Initiation',
        phaseSummary: 'Gate the request, verify scope, and create the audit case.',
        title: 'Receive Audit Request',
        description: 'A company request or compliance trigger should exist before the case can move.',
        signal: company?.companyName
          ? `Case ${this.caseId()} is linked to ${company.companyName}.`
          : `Company ${this.companyId()} has not produced a full company record yet.`,
        completed: Boolean(company),
      },
      {
        phaseId: 'initiation',
        phaseTitle: 'Phase 0: Initiation',
        phaseSummary: 'Gate the request, verify scope, and create the audit case.',
        title: 'Verify Eligibility',
        description: 'Confirm regulatory thresholds and whether EN 16247-1 applies.',
        signal: hasEligibilitySignals
          ? `Eligibility signals are present through obligation, size, revenue, or energy fields.`
          : `No regulatory threshold signal is available yet.`,
        completed: hasEligibilitySignals,
      },
      {
        phaseId: 'initiation',
        phaseTitle: 'Phase 0: Initiation',
        phaseSummary: 'Gate the request, verify scope, and create the audit case.',
        title: 'Create Audit Record',
        description: 'Open the case record and bind it to the auditee.',
        signal: company?.id
          ? `Company record ${company.id} is available for this case.`
          : `The company endpoint has not returned a persisted record yet.`,
        completed: Boolean(company?.id),
      },
      {
        phaseId: 'initiation',
        phaseTitle: 'Phase 0: Initiation',
        phaseSummary: 'Gate the request, verify scope, and create the audit case.',
        title: 'Assign Audit Team',
        description: 'Nominate the lead auditor and supporting team.',
        signal: hasTeam
          ? `${company?.users?.length ?? 0} user account(s) are linked to the company.`
          : `No assigned audit users are visible on the company record.`,
        completed: hasTeam,
      },
      {
        phaseId: 'planning',
        phaseTitle: 'Phase 1: Planning',
        phaseSummary: 'Gather input material, establish the baseline, and arrange the visit.',
        title: 'Request Documentation',
        description: 'Collect site documents, bills, and prior audit context.',
        signal: hasContacts
          ? `Contact details are available for the document request pack.`
          : `Contact metadata is still missing from the company record.`,
        completed: hasContacts,
      },
      {
        phaseId: 'planning',
        phaseTitle: 'Phase 1: Planning',
        phaseSummary: 'Gather input material, establish the baseline, and arrange the visit.',
        title: 'Collect Energy Data',
        description: 'Use CSV imports or manual entry to assemble the source dataset.',
        signal: uploadsInScope.length
          ? `${uploadsInScope.length} upload(s) are available in the evidence queue.`
          : `No in-scope CSV uploads are available yet.`,
        completed: hasEnergyData,
      },
      {
        phaseId: 'planning',
        phaseTitle: 'Phase 1: Planning',
        phaseSummary: 'Gather input material, establish the baseline, and arrange the visit.',
        title: 'Calculate Baseline SEU',
        description: 'Establish the significant energy use baseline before field work.',
        signal: hasBaselineData
          ? `Energy baseline fields are present on the company or site records.`
          : `Baseline values have not been persisted yet.`,
        completed: hasBaselineData,
      },
      {
        phaseId: 'planning',
        phaseTitle: 'Phase 1: Planning',
        phaseSummary: 'Gather input material, establish the baseline, and arrange the visit.',
        title: 'Schedule Site Visit',
        description: 'Confirm the site scope and visit sequence with the auditee.',
        signal: hasSites
          ? `${company?.sites?.length ?? 0} site record(s) can anchor the visit plan.`
          : `No site records are available for visit planning yet.`,
        completed: hasSites,
      },
      {
        phaseId: 'execution',
        phaseTitle: 'Phase 2: Execution',
        phaseSummary: 'Run the visit, inspect the plant, and capture evidence.',
        title: 'Conduct Opening Meeting',
        description: 'Kick off the audit on site and align on access, safety, and agenda.',
        signal: hasExecutionEvidence
          ? `Processed evidence suggests field activity is already underway.`
          : `No processed field evidence is visible yet.`,
        completed: hasExecutionEvidence,
      },
      {
        phaseId: 'execution',
        phaseTitle: 'Phase 2: Execution',
        phaseSummary: 'Run the visit, inspect the plant, and capture evidence.',
        title: 'Physical Inspection',
        description: 'Inspect buildings, systems, and major energy users.',
        signal: hasSites
          ? `Site records exist, but inspection completion depends on field evidence.`
          : `The inspection scope is still underspecified because site records are missing.`,
        completed: hasExecutionEvidence && hasSites,
      },
      {
        phaseId: 'execution',
        phaseTitle: 'Phase 2: Execution',
        phaseSummary: 'Run the visit, inspect the plant, and capture evidence.',
        title: 'Collect Evidence',
        description: 'Capture measurements, photos, invoices, and sub-meter logs.',
        signal: processedUploads.length
          ? `${processedUploads.length} upload(s) have already been processed.`
          : `No processed uploads are available as evidence yet.`,
        completed: hasExecutionEvidence,
      },
      {
        phaseId: 'execution',
        phaseTitle: 'Phase 2: Execution',
        phaseSummary: 'Run the visit, inspect the plant, and capture evidence.',
        title: 'Complete Audit Checklist',
        description: 'Close the on-site checklist and outstanding evidence gaps.',
        signal: hasChecklistSignals
          ? `Successful imports or audit notes indicate checklist work has started.`
          : `Checklist completion still lacks a strong data signal.`,
        completed: hasChecklistSignals,
      },
      {
        phaseId: 'analysis',
        phaseTitle: 'Phase 3: Analysis',
        phaseSummary: 'Normalize data, identify measures, and draft the findings.',
        title: 'Normalize Energy Data',
        description: 'Clean, reconcile, and normalize the captured consumption data.',
        signal: successfulUploads.length
          ? `${successfulUploads.length} upload(s) completed successfully or partially.`
          : `No successful import exists to normalize yet.`,
        completed: successfulUploads.length > 0,
      },
      {
        phaseId: 'analysis',
        phaseTitle: 'Phase 3: Analysis',
        phaseSummary: 'Normalize data, identify measures, and draft the findings.',
        title: 'Identify Savings Opportunities',
        description: 'Translate the field and data review into energy-saving measures.',
        signal: hasAnalysisSignals
          ? `Benchmarks or validated uploads support opportunity identification.`
          : `The dataset is still too thin to support opportunity mapping.`,
        completed: hasAnalysisSignals,
      },
      {
        phaseId: 'analysis',
        phaseTitle: 'Phase 3: Analysis',
        phaseSummary: 'Normalize data, identify measures, and draft the findings.',
        title: 'Calculate ROI & Payback',
        description: 'Quantify the commercial case for shortlisted measures.',
        signal: hasClosedAudit
          ? `A completed audit date suggests the financial analysis is already closed.`
          : `No ROI or payback artifact is exposed by the current backend model.`,
        completed: hasClosedAudit,
      },
      {
        phaseId: 'analysis',
        phaseTitle: 'Phase 3: Analysis',
        phaseSummary: 'Normalize data, identify measures, and draft the findings.',
        title: 'Generate Draft Report',
        description: 'Compile the first complete audit narrative for internal review.',
        signal: hasClosedAudit
          ? `The audit has a recorded completion date, which implies a report trail exists.`
          : `The draft report stage is not yet evidenced by the current data.`,
        completed: hasClosedAudit,
      },
      {
        phaseId: 'finalization',
        phaseTitle: 'Phase 4: Finalization',
        phaseSummary: 'Issue the final report and close the compliance deliverables.',
        title: 'Generate Final PDF',
        description: 'Freeze the approved report as the issued audit deliverable.',
        signal: hasClosedAudit
          ? `Last audit date ${company?.lastAuditDate} marks the audit as finalized in the record.`
          : `No final report issuance marker is visible yet.`,
        completed: hasClosedAudit,
      },
      {
        phaseId: 'finalization',
        phaseTitle: 'Phase 4: Finalization',
        phaseSummary: 'Issue the final report and close the compliance deliverables.',
        title: 'Deliver to Company',
        description: 'Share the final package with the auditee and capture delivery.',
        signal: hasClosedAudit && hasContacts
          ? `The company has contact metadata for final delivery.`
          : `Delivery cannot be confirmed without a finished audit and contact path.`,
        completed: hasClosedAudit && hasContacts,
      },
      {
        phaseId: 'filing',
        phaseTitle: 'Mandatory Filing',
        phaseSummary: 'Submit the audit externally when the company is in scope.',
        title: 'Submit to Authority',
        description: 'Only applies when filing with an authority is mandatory.',
        signal: needsMandatoryFiling
          ? `The record indicates a mandatory or scheduled compliance obligation.`
          : `This branch is not required for the current company record.`,
        completed: hasSubmissionSignal,
        skipped: !needsMandatoryFiling,
      },
      {
        phaseId: 'filing',
        phaseTitle: 'Mandatory Filing',
        phaseSummary: 'Submit the audit externally when the company is in scope.',
        title: 'Record Submission ID',
        description: 'Store the filing reference once the authority accepts the package.',
        signal: hasSubmissionSignal
          ? `Company status suggests an external submission has been recorded.`
          : needsMandatoryFiling
            ? `No submission identifier is exposed by the backend model yet.`
            : `No authority submission is needed for this case.`,
        completed: hasSubmissionSignal,
        skipped: !needsMandatoryFiling,
      },
    ];

    const firstOpenStepIndex = stepDefinitions.findIndex((step) => !step.completed && !step.skipped);

    const phaseOrder = ['initiation', 'planning', 'execution', 'analysis', 'finalization', 'filing'];
    const phases = phaseOrder
      .map((phaseId) => {
        const phaseSteps = stepDefinitions.filter((step) => step.phaseId === phaseId);
        if (!phaseSteps.length) {
          return null;
        }

        const steps = phaseSteps.map((stepDefinition, stepIndex) => {
          const globalIndex = stepDefinitions.findIndex(
            (step) => step.phaseId === phaseId && step.title === stepDefinition.title,
          );
          const status: StepStatus = stepDefinition.skipped
            ? 'skipped'
            : stepDefinition.completed
              ? 'done'
              : globalIndex === firstOpenStepIndex
                ? 'current'
                : 'upcoming';

          return {
            title: stepDefinition.title,
            description: stepDefinition.description,
            signal: stepDefinition.signal,
            status,
          };
        });

        const actionableSteps = steps.filter((step) => step.status !== 'skipped');
        const completedSteps = actionableSteps.filter((step) => step.status === 'done').length;
        const hasCurrentStep = steps.some((step) => step.status === 'current');
        const phaseStatus: Exclude<StepStatus, 'skipped'> =
          actionableSteps.length > 0 && completedSteps === actionableSteps.length
            ? 'done'
            : hasCurrentStep
              ? 'current'
              : 'upcoming';

        return {
          id: phaseId,
          title: phaseSteps[0].phaseTitle,
          summary: phaseSteps[0].phaseSummary,
          status: phaseStatus,
          completedSteps,
          totalSteps: actionableSteps.length,
          steps,
        } satisfies AuditPhaseViewModel;
      })
      .filter((phase): phase is AuditPhaseViewModel => phase !== null);

    const evidenceScopeLabel =
      company?.id && uploads.some((upload) => upload.company?.id === company.id)
        ? `Showing uploads linked to company ${company.id}.`
        : uploadsInScope.length
          ? 'Showing unlinked CSV uploads as shared evidence signals because no company-linked uploads were found.'
          : 'No CSV uploads are currently available for this case.';

    return {
      phases,
      uploadsInScope,
      evidenceScopeLabel,
      lastAuditDate: company?.lastAuditDate ?? null,
      regulatoryObligation: company?.regulatoryObligation ?? null,
    };
  }

  private resolveUploadsInScope(company: CompanyModel | null, uploads: CSVUploadModel[]): CSVUploadModel[] {
    if (!uploads.length) {
      return [];
    }

    if (!company?.id) {
      return uploads.filter((upload) => !upload.company?.id);
    }

    const linkedUploads = uploads.filter((upload) => upload.company?.id === company.id);
    if (linkedUploads.length) {
      return linkedUploads;
    }

    return uploads.filter((upload) => !upload.company?.id);
  }

  private needsMandatoryFiling(company: CompanyModel | null): boolean {
    const obligation = (company?.regulatoryObligation ?? '').toLowerCase();

    return (
      obligation.includes('mandatory') ||
      obligation.includes('required') ||
      obligation.includes('oblig') ||
      Boolean(company?.nextMandatoryAuditDate)
    );
  }
}
