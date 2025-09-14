package com.example.FileManagementAndStorage.Repository;

import com.example.FileManagementAndStorage.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<Object> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);
}
