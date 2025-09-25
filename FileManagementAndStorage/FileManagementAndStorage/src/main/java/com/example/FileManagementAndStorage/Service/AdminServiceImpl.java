package com.example.FileManagementAndStorage.Service;

import com.example.FileManagementAndStorage.Model.UserEntity;
import com.example.FileManagementAndStorage.ModelDTO.FileDTO;
import com.example.FileManagementAndStorage.ModelDTO.UserDTO;
import com.example.FileManagementAndStorage.Repository.FileRepository;
import com.example.FileManagementAndStorage.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public List<FileDTO> getAllFiles() {
        return fileRepository.findAll()
                .stream()
                .map(file -> modelMapper.map(file, FileDTO.class))
                .collect(Collectors.toList());
    }
}
