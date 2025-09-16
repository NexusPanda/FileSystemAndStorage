package com.example.FileManagementAndStorage.ModelDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDTO {
    private Long id;
    private String fileName;
    private String type;
    private Long size;
    private String path;
    private LocalDateTime createdAt;
    private boolean deleted;
    private Long folderId;
}
