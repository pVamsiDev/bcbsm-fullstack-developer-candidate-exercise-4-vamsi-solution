package com.vamsi.FilesManager.controllers;

import com.vamsi.FilesManager.security.jwt.AuthEntryPointJwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);
    @PostMapping("/upload")
    public ResponseEntity<Object> uploadAndCompress(@RequestParam("files") MultipartFile[] files) {
        String uploadDir = "uploads";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        try {
            String timestamp = String.valueOf(System.currentTimeMillis());
            String fileStoragePath = uploadDir + File.separator + timestamp;
            File fileStorageDir = new File(fileStoragePath);
            if (!fileStorageDir.exists()) {
                fileStorageDir.mkdirs();
            }

            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String fileName = file.getOriginalFilename();
                    String filePath = fileStoragePath + File.separator + fileName;
                    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
                        byte[] bytes = file.getBytes();
                        bos.write(bytes);
                    }
                }
            }

            // for compressing the uploaded files to zip archive
            String zipFileName = uploadDir + File.separator + timestamp + ".zip";
            if (compressFiles(fileStoragePath, zipFileName)) {
                File zipFile = new File(zipFileName);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.valueOf("application/zip"));
                headers.setContentDispositionFormData("attachment", zipFile.getName());
                headers.setContentLength(zipFile.length());

                InputStreamResource resource = new InputStreamResource(new FileInputStream(zipFile));
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(resource);
            } else {
                return new ResponseEntity<>("Failed to compress files.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IOException e) {
            logger.error("Error during file upload: {}", e);
            return new ResponseEntity<>("Error during file upload and compression.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    boolean compressFiles(String sourceDir, String zipFileName) {
        try (FileOutputStream fos = new FileOutputStream(zipFileName);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {

            File sourceFolder = new File(sourceDir);
            for (File fileToZip : sourceFolder.listFiles()) {
                FileInputStream fis = new FileInputStream(fileToZip);
                ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                zipOut.putNextEntry(zipEntry);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) >= 0) {
                    zipOut.write(buffer, 0, length);
                }

                fis.close();
            }
            return true;
        } catch (IOException e) {
            logger.error("Error during file compress: {}", e);
            return false;
        }
    }
}
