package com.example.FileManagementAndStorage.Service;

import com.example.FileManagementAndStorage.Exception.ResourceNotFoundException;
import com.example.FileManagementAndStorage.Model.FileModel;
import com.example.FileManagementAndStorage.Model.Folder;
import com.example.FileManagementAndStorage.Model.UserEntity;
import com.example.FileManagementAndStorage.ModelDTO.FileDTO;
import com.example.FileManagementAndStorage.Repository.FileRepository;
import com.example.FileManagementAndStorage.Repository.FolderRepository;
import com.example.FileManagementAndStorage.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.UUID;

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

    @Autowired
    private S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public FileDTO uploadFile(MultipartFile multipartFile, Long folderId, String username) {
        try {
            UserEntity owner = (UserEntity) userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "Username", username));

            Folder folder = null;
            if (folderId != null) {
                folder = folderRepository.findById(folderId)
                        .orElseThrow(() -> new ResourceNotFoundException("Folder", "Folder_Id", folderId));
            }

            // Generate unique S3 object key
            String key = owner.getUsername() + "/" + UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

            // Upload to S3
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(key)
                            .contentType(multipartFile.getContentType())
                            .build(),
                    RequestBody.fromBytes(multipartFile.getBytes())
            );

            FileModel file = FileModel.builder()
                    .fileName(multipartFile.getOriginalFilename())
                    .type(multipartFile.getContentType())
                    .size(multipartFile.getSize())
                    .path(key) // store only S3 key
                    .owner(owner)
                    .folder(folder)
                    .isDeleted(false)
                    .build();

            FileModel saved = fileRepository.save(file);
            return modelMapper.map(saved, FileDTO.class);

        } catch (IOException e) {
            throw new RuntimeException("Error uploading file to S3", e);
        }
    }

    @Override
    public ResponseEntity<byte[]> downloadFile(Long id) {
        FileModel file = fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File", "File_Id", id));

        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(file.getPath()) // stored S3 key
                    .build();

            ResponseBytes<GetObjectResponse> objectBytes =
                    s3Client.getObjectAsBytes(getObjectRequest);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + file.getFileName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, file.getType())
                    .body(objectBytes.asByteArray());

        } catch (S3Exception e) {
            throw new RuntimeException("Error downloading file from S3", e);
        }
    }

    @Override
    public void softDelete(Long id) {
        FileModel file = fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File", "File_Id", id));
        file.setDeleted(true);
        fileRepository.save(file);
    }

    @Override
    public void restoreFile(Long id) {
        FileModel file = fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File", "File_Id", id));
        file.setDeleted(false);
        fileRepository.save(file);
    }

    @Override
    public void permanentDelete(Long id) {
        FileModel file = fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File", "File_Id", id));

        // Delete from S3
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(file.getPath())
                .build());

        fileRepository.delete(file);
    }
}
