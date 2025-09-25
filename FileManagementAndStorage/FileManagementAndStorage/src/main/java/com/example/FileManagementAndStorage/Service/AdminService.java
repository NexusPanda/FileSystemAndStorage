package com.example.FileManagementAndStorage.Service;

import com.example.FileManagementAndStorage.ModelDTO.FileDTO;
import com.example.FileManagementAndStorage.ModelDTO.UserDTO;

import java.util.List;

public interface AdminService {
    List<UserDTO> getAllUsers();
    void deleteUser(Long id);
    List<FileDTO> getAllFiles();
}
