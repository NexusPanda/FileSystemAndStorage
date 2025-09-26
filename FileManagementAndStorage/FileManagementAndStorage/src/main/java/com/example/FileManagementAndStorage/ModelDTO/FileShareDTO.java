package com.example.FileManagementAndStorage.ModelDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileShareDTO {
    private Long id;
    private Long fileId;
    private Long sharedWithUserId;
    private String permission; // optional
}
