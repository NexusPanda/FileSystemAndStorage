package com.example.FileManagementAndStorage.Repository;

import com.example.FileManagementAndStorage.Model.FileShare;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileShareRepository extends JpaRepository<FileShare, Long> {
}
