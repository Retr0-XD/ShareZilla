package com.jpa.sharezillamain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

// model/FileMetadata.java
@Entity
@Data
public class FileMetadata {
    @Id
    @GeneratedValue
    private Long id;
    private String filename;
    private String username;
    private Long size;
    private LocalDateTime uploadedAt;

    // getters and setters
}