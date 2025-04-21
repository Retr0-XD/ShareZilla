package com.jpa.sharezillamain.controller;

import com.jpa.sharezillamain.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileStorageService fileService;

    // Accessible to UPLOADER and ADMIN
    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('UPLOADER', 'ADMIN')")
    public ResponseEntity<?> listFiles(Authentication authentication) {
        return ResponseEntity.ok(fileService.listFiles(authentication.getName()));
    }

    // Accessible to UPLOADER and ADMIN
    @PostMapping("/upload")
    @PreAuthorize("hasAnyRole('UPLOADER', 'ADMIN')")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, Authentication authentication) throws IOException {
        fileService.store(file, authentication.getName());
        return ResponseEntity.ok("File uploaded");
    }

    // Accessible to UPLOADER and ADMIN
    @GetMapping("/download/{filename:.+}")
    @PreAuthorize("hasAnyRole('UPLOADER', 'ADMIN')")
    public ResponseEntity<Resource> download(@PathVariable String filename, Authentication authentication) throws IOException {
        // Construct file path
        Path filePath = Paths.get("storage", authentication.getName(), filename);
        System.out.println("Trying to download from: " + filePath.toString());

        // Check if the file exists
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("File not found: " + filename);
        }

        // Create a Resource from the file
        Resource resource = new FileSystemResource(filePath);

        // Set the content type and return the file
        String contentType = Files.probeContentType(filePath);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType != null ? contentType : "application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }


    // Accessible to UPLOADER and ADMIN
    @DeleteMapping("/delete/{filename}")
    @PreAuthorize("hasAnyRole('UPLOADER', 'ADMIN')")
    public ResponseEntity<?> delete(@PathVariable String filename, Authentication authentication) throws IOException {
        fileService.delete(filename, authentication.getName());
        return ResponseEntity.ok("File deleted");
    }
}
