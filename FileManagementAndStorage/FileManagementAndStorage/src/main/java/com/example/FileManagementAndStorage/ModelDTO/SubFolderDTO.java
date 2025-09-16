package com.example.FileManagementAndStorage.ModelDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubFolderDTO {
    private Long id;
    private String folderName;
}
