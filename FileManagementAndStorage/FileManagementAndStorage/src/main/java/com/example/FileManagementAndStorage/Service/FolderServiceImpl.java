package com.example.FileManagementAndStorage.Service;

import com.example.FileManagementAndStorage.Exception.ResourceNotFoundException;
import com.example.FileManagementAndStorage.Model.Folder;
import com.example.FileManagementAndStorage.Model.UserEntity;
import com.example.FileManagementAndStorage.ModelDTO.FolderDTO;
import com.example.FileManagementAndStorage.Repository.FolderRepository;
import com.example.FileManagementAndStorage.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FolderServiceImpl implements FolderService {

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FolderDTO createFolder(String name, Long parentId, String username) {

//        System.out.println("Name from Principle = " + username);
        UserEntity owner = (UserEntity) userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User","Username","User"));

        Folder folder = new Folder();
        folder.setFolderName(name);
        folder.setOwner(owner);
        folder.setCreatedAt(LocalDateTime.now());

        if (parentId != null) {
            Folder parent = folderRepository.findById(parentId)
                    .orElseThrow(() -> new RuntimeException("Parent folder not found"));
            folder.setParentFolder(parent);
        }

        Folder saved = folderRepository.save(folder);
        return modelMapper.map(saved, FolderDTO.class);
    }

    @Override
    public FolderDTO getFolder(Long id) {
        Folder folder = folderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Folder id","Folder",id));
        return modelMapper.map(folder, FolderDTO.class);
    }

    @Override
    public FolderDTO renameFolder(Long id, String folderName) {
        Folder folder = folderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Folder id","Folder",id));
        folder.setFolderName(folderName);
        Folder updated = folderRepository.save(folder);
        return modelMapper.map(updated, FolderDTO.class);
    }

    @Override
    public void deleteFolder(Long id) {
        Folder folder = folderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Folder id","Folder",id));
        folderRepository.delete(folder);
    }
}
