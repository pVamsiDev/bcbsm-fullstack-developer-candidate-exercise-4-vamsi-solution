import { Component } from '@angular/core';
import { AccountService, AlertService } from '../services';
import { UploadService } from '../services/upload.service';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent {
  selectedFiles: File[] = [];

  constructor(private alertService: AlertService, private uploadService: UploadService) {}

  onFileSelected(event: any): void {
    this.selectedFiles = event.target.files;
  }

  compressFiles(): void {
    if (this.selectedFiles.length === 0) {
      console.log('No files selected.');
      return;
    }

    const formData = new FormData();
    for (const file of this.selectedFiles) {
      formData.append('files', file);
    }

    const observer = {
      next: (response) => {
        this.alertService.success('Compression successful', { keepAfterRouteChange: false });
      },
      error: (error) => {
        console.error('Compression error:', error);
        this.alertService.error("Compression error");
      }
    };

    this.uploadService.upload(formData).subscribe(observer);
  }
}

