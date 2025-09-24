package com.example.FileManagementAndStorage.Service;

import com.example.FileManagementAndStorage.Model.FileModel;
import com.example.FileManagementAndStorage.Model.Folder;
import com.example.FileManagementAndStorage.Model.UserEntity;
import com.example.FileManagementAndStorage.ModelDTO.FileDTO;
import com.example.FileManagementAndStorage.Repository.FileRepository;
import com.example.FileManagementAndStorage.Repository.FolderRepository;
import com.example.FileManagementAndStorage.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FileDTO uploadFile(MultipartFile multipartFile, Long folderId, String username) {
        try {
            UserEntity owner = (UserEntity) userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Folder folder = null;
            if (folderId != null) {
                folder = folderRepository.findById(folderId)
                        .orElseThrow(() -> new RuntimeException("Folder not found"));
            }

            // Define storage path (example: uploads/<username>/<filename>)
            String storageDir = "uploads/" + owner.getUsername();
            java.nio.file.Path dirPath = java.nio.file.Paths.get(storageDir);
            if (!java.nio.file.Files.exists(dirPath)) {
                java.nio.file.Files.createDirectories(dirPath);
            }

            java.nio.file.Path filePath = dirPath.resolve(multipartFile.getOriginalFilename());
            multipartFile.transferTo(filePath); // Save file physically

            FileModel file = FileModel.builder()
                    .fileName(multipartFile.getOriginalFilename())
                    .type(multipartFile.getContentType())
                    .size(multipartFile.getSize())
                    .path(filePath.toString())   // ✅ IMPORTANT
                    .owner(owner)
                    .folder(folder)
                    .isDeleted(false)
                    .build();

            FileModel saved = fileRepository.save(file);
            return modelMapper.map(saved, FileDTO.class);

        } catch (IOException e) {
            throw new RuntimeException("Error storing file", e);
        }
    }

    @Override
    public ResponseEntity<byte[]> downloadFile(Long id) {
        FileModel file = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));
        try {
            Path path = Paths.get(file.getPath());
            byte[] data = Files.readAllBytes(path);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + file.getFileName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, file.getType())
                    .body(data);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file", e);
        }
    }

    @Override
    public void softDelete(Long id) {
        FileModel file = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));
        file.setDeleted(true);
        fileRepository.save(file);
    }

    @Override
    public void restoreFile(Long id) {
        FileModel file = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));
        file.setDeleted(false);
        fileRepository.save(file);
    }

    @Override
    public void permanentDelete(Long id) {
        FileModel file = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));
        fileRepository.delete(file);
    }
}
