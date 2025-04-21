package com.jpa.sharezillamain.service;

import com.jpa.sharezillamain.model.FileMetadata;
import com.jpa.sharezillamain.repository.FileMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

// service/FileStorageService.java
@Service
public class FileStorageService {


    private final Path storageRoot = Paths.get("storage");

    @Autowired
    private FileMetadataRepository repo;


    public void store(MultipartFile file, String username) throws IOException {

        // Create the root storage directory if it doesn't exist
        if (!Files.exists(storageRoot)) {
            Files.createDirectories(storageRoot);
        }
        // Make sure "storage/username" exists as a directory
        Path userDir = Paths.get("storage", username);
        Files.createDirectories(userDir); // This creates the folder safely

        // Construct full file path
        Path filePath = userDir.resolve(Objects.requireNonNull(file.getOriginalFilename()));

        // Save the file
        Files.write(filePath, file.getBytes());

        file.transferTo(filePath);

        // Optionally save metadata to DB
        FileMetadata metadata = new FileMetadata();
        metadata.setFilename(file.getOriginalFilename());
        metadata.setUsername(username);
        repo.save(metadata);
    }



    public List<FileMetadata> listFiles(String username) {
        return repo.findByUsername(username);
    }

    public ResponseEntity<Resource> download(String filename, String username) throws IOException {
        Path filePath = storageRoot.resolve(username).resolve(filename);
        if (!Files.exists(filePath)) throw new FileNotFoundException();
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(filePath));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    public void delete(String filename, String username) throws IOException {
        Path filePath = storageRoot.resolve(username).resolve(filename);
        Files.deleteIfExists(filePath);
        repo.findByUsernameAndFilename(username, filename)
                .ifPresent(repo::delete);
    }
}