package com.example.FileManagementAndStorage.Controller;


import com.example.FileManagementAndStorage.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/files/upload")
    public ResponseEntity<?> uploadFile(MultipartFile multipartFile){

    }

    @GetMapping("/files/{id}/download")
    public ResponseEntity<?> getFile(){

    }

    @DeleteMapping("/files/{id}")
    public ResponseEntity<?> softDelete(){

    }

    @PatchMapping("/files/{id}/restore")
    public ResponseEntity<?> restore(){

    }

    @DeleteMapping("/files/{id}/permanent")
    public ResponseEntity<?> permanentDelete(){

    }
}
