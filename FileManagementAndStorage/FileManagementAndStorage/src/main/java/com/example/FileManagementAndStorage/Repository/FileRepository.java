package com.example.FileManagementAndStorage.Repository;

import com.example.FileManagementAndStorage.Model.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileModel, Long> {
}
