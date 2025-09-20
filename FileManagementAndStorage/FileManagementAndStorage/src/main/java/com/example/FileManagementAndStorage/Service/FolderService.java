package com.example.FileManagementAndStorage.Service;

import com.example.FileManagementAndStorage.ModelDTO.FolderDTO;

public interface FolderService {
    FolderDTO createFolder(String name, Long id, String username);

    FolderDTO getFolder(Long id);

    FolderDTO renameFolder(Long id, String newName);

    void deleteFolder(Long id);
}