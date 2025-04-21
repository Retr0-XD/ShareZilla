package com.jpa.sharezillamain.repository;

import com.jpa.sharezillamain.model.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// repository/FileMetadataRepository.java
public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
    List<FileMetadata> findByUsername(String username);
    Optional<FileMetadata> findByUsernameAndFilename(String username, String filename);
}