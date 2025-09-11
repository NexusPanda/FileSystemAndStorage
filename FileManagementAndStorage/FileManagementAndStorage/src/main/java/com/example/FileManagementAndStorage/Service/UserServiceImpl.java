package com.example.FileManagementAndStorage.Service;

import com.example.FileManagementAndStorage.Model.User;
import com.example.FileManagementAndStorage.ModelDTO.UserDTO;
import com.example.FileManagementAndStorage.Repository.UserRepository;
import com.example.FileManagementAndStorage.Security.Request.LoginRequest;
import com.example.FileManagementAndStorage.Security.Request.SignUpRequest;
import com.example.FileManagementAndStorage.Security.Response.LoginResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO register(SignUpRequest signup) {
        User user = userRepository.findByUsername(signup.getUsername());
        if(user != null){
            throw new RuntimeException("User with this Username is already exists !!!");
        }
        User user1 = modelMapper.map(signup, User.class);
        userRepository.save(user1);
        return modelMapper.map(user1, UserDTO.class);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return null;
    }
}
