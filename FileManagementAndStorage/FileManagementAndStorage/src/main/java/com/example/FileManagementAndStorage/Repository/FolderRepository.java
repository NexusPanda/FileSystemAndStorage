package com.example.FileManagementAndStorage.Repository;

import com.example.FileManagementAndStorage.Model.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<Folder, Long> {
}
