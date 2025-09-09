package com.example.FileManagementAndStorage.Model;

import com.example.FileManagementAndStorage.Config.Permission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileShare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // File being shared
    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    private FileModel file;

    // User with whom it is shared
    @ManyToOne
    @JoinColumn(name = "shared_with", nullable = false)
    private User sharedWith;

    @Enumerated(EnumType.STRING)
    private Permission permission = Permission.READ;

    private LocalDateTime sharedAt = LocalDateTime.now();

}
