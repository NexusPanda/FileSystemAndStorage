package com.example.FileManagementAndStorage.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    private String type;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false)
    private String path;

    private boolean isDeleted = false;

    private LocalDateTime createdAt = LocalDateTime.now();

    // Owner of file
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity owner;

    // Folder where file is stored
    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    // File sharing info
    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileShare> shares;

}
