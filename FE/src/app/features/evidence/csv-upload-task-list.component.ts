import { CommonModule, DatePipe, DecimalPipe } from '@angular/common';
import { Component, computed, input, signal } from '@angular/core';
import { CSVUploadModel } from '../../models/csv-upload.model';

@Component({
  selector: 'app-csv-upload-task-list',
  standalone: true,
  imports: [CommonModule, DatePipe, DecimalPipe],
  templateUrl: './csv-upload-task-list.component.html',
  styleUrl: './csv-upload-task-list.component.scss',
})
export class CsvUploadTaskListComponent {
  readonly uploads = input<CSVUploadModel[]>([]);
  readonly loading = input(false);
  readonly errorMessage = input<string | null>(null);

  protected readonly searchTerm = signal('');
  protected readonly selectedStatus = signal('all');
  protected readonly selectedUploadId = signal<number | null>(null);

  protected readonly availableStatuses = computed(() => {
    const statuses = new Set(
      this.uploads()
        .map((upload) => upload.importStatus?.trim())
        .filter((status): status is string => Boolean(status)),
    );

    return ['all', ...Array.from(statuses)];
  });

  protected readonly filteredUploads = computed(() => {
    const searchTerm = this.searchTerm().trim().toLowerCase();
    const selectedStatus = this.selectedStatus().toLowerCase();

    return this.uploads().filter((upload) => {
      const matchesStatus =
        selectedStatus === 'all' || (upload.importStatus ?? '').toLowerCase() === selectedStatus;
      const haystack = [
        upload.fileName,
        upload.uploadedBy,
        upload.notes,
        upload.company?.companyName,
        upload.site?.siteName,
        upload.meterType?.typeName,
      ]
        .filter(Boolean)
        .join(' ')
        .toLowerCase();

      const matchesSearch = !searchTerm || haystack.includes(searchTerm);

      return matchesStatus && matchesSearch;
    });
  });

  protected readonly selectedUpload = computed(() => {
    const selectedUploadId = this.selectedUploadId();

    if (selectedUploadId === null) {
      return this.filteredUploads()[0] ?? null;
    }

    return (
      this.filteredUploads().find((upload) => upload.id === selectedUploadId) ??
      this.filteredUploads()[0] ??
      null
    );
  });

  protected setSearchTerm(value: string): void {
    this.searchTerm.set(value);
  }

  protected setSelectedStatus(value: string): void {
    this.selectedStatus.set(value);
    this.selectedUploadId.set(null);
  }

  protected selectUpload(uploadId?: number): void {
    if (uploadId === undefined) {
      return;
    }

    this.selectedUploadId.set(uploadId);
  }

  protected isSelected(upload: CSVUploadModel): boolean {
    return this.selectedUpload()?.id === upload.id;
  }

  protected totalHandledRecords(upload: CSVUploadModel): number {
    return (
      (upload.successfulRecords ?? 0) +
      (upload.failedRecords ?? 0) +
      (upload.skippedRecords ?? 0)
    );
  }

  protected formatStatus(status?: string): string {
    if (!status) {
      return 'Unknown';
    }

    return status
      .toLowerCase()
      .split('_')
      .map((segment) => segment.charAt(0).toUpperCase() + segment.slice(1))
      .join(' ');
  }

  protected statusClass(status?: string): string {
    switch (status) {
      case 'SUCCESS':
        return 'bg-emerald-100 text-emerald-800 dark:bg-emerald-400/15 dark:text-emerald-200';
      case 'PARTIAL_SUCCESS':
        return 'bg-amber-100 text-amber-800 dark:bg-amber-400/15 dark:text-amber-200';
      case 'FAILED':
        return 'bg-rose-100 text-rose-800 dark:bg-rose-400/15 dark:text-rose-200';
      case 'IN_PROGRESS':
        return 'bg-sky-100 text-sky-800 dark:bg-sky-400/15 dark:text-sky-200';
      default:
        return 'bg-slate-100 text-slate-700 dark:bg-slate-400/15 dark:text-slate-200';
    }
  }
}
