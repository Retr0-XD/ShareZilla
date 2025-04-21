package com.jpa.sharezillamain.controller;

import com.jpa.sharezillamain.model.PublicData;
import com.jpa.sharezillamain.repository.PublicDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/public-data")
public class PublicDataController {

    @Autowired
    private PublicDataRepository repo;

    // ⬇️ Accessible by all roles
    @GetMapping
    public ResponseEntity<List<PublicData>> getAll() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicData> getById(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable Long id) {

        PublicData data = repo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));

        try {
            Path file = Paths.get(data.getFilePath()); // or root + data.getFilePath()
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName().toString() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(404).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // ⬇️ ADMIN ONLY
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PublicData> create(@RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("content") String content) throws IOException {
        
        // Define `storage/` directory path
        String storageDir = "public-data";
        File dir = new File(storageDir);
        if (!dir.exists()) {
            dir.mkdirs(); // make sure folder exists
        }

        // Save file to storage/
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(storageDir, fileName);
        Files.write(filePath, file.getBytes());

        // Save record with local file path
        PublicData publicData = new PublicData(title, content, filePath.toString());
        PublicData saved = repo.save(publicData);

        return ResponseEntity.ok(saved);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestParam("title") String title,
                                    @RequestParam("content") String content,
                                    @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        return repo.findById(id).map(existing -> {
            existing.setTitle(title);
            existing.setContent(content);

            if (file != null && !file.isEmpty()) {
                try {
                    String storageDir = "public-data";
                    File dir = new File(storageDir);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    Path filePath = Paths.get(storageDir, fileName);
                    Files.write(filePath, file.getBytes());

                    existing.setFilePath(filePath.toString());
                } catch (IOException e) {
                    return ResponseEntity.status(500).body("Error saving file: " + e.getMessage());
                }
            }

            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
