package com.example.FileManagementAndStorage.Service;

import com.example.FileManagementAndStorage.ModelDTO.FileDTO;
import com.example.FileManagementAndStorage.Repository.FileRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService{

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FileDTO uploadFile(MultipartFile multipartFile, Long folderId) {
        return null;
    }

    @Override
    public ResponseEntity<?> downloadFile(Long id) {
        return null;
    }

    @Override
    public void softDelete(Long id) {

    }

    @Override
    public void restoreFile(Long id) {

    }

    @Override
    public void permanentDelete(Long id) {

    }
}
