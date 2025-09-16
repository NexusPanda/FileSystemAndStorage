package com.example.FileManagementAndStorage.Controller;

import com.example.FileManagementAndStorage.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam(value = "folderId", required = false) Long folderId) {

        return ResponseEntity.ok(fileService.uploadFile(multipartFile, folderId));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<?> getFile(@PathVariable Long id) {
        return fileService.downloadFile(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> softDelete(@PathVariable Long id) {
        fileService.softDelete(id);
        return ResponseEntity.ok("File moved to trash");
    }

    @PatchMapping("/{id}/restore")
    public ResponseEntity<?> restore(@PathVariable Long id) {
        fileService.restoreFile(id);
        return ResponseEntity.ok("File restored");
    }

    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<?> permanentDelete(@PathVariable Long id) {
        fileService.permanentDelete(id);
        return ResponseEntity.noContent().build();
    }
}
