import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class UploadService {

  constructor(private http: HttpClient) { }

  upload(formData) {
    return this.http.post(`${environment.apiUrl}/api/auth/upload`, formData, {responseType: 'blob'}).pipe(
        map((data: any) => {
            // console.log("data", data);
            const url = window.URL.createObjectURL(new Blob([data], { type: "application/zip" }));
            const link = document.createElement("a");
            link.href = url;
            link.setAttribute("download", "download.zip");
            document.body.appendChild(link);
            link.click();
            link.parentNode.removeChild(link);
            return data;
        })
    );
}
}
