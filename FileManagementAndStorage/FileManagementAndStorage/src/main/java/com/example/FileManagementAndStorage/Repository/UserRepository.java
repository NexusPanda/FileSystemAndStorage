package com.example.FileManagementAndStorage.Repository;

import com.example.FileManagementAndStorage.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
