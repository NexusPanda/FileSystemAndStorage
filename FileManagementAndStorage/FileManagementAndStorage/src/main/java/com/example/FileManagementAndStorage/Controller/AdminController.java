package com.example.FileManagementAndStorage.Controller;

import com.example.FileManagementAndStorage.ModelDTO.FileDTO;
import com.example.FileManagementAndStorage.ModelDTO.UserDTO;
import com.example.FileManagementAndStorage.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/files")
    public ResponseEntity<List<FileDTO>> getAllFiles() {
        return ResponseEntity.ok(adminService.getAllFiles());
    }
}
