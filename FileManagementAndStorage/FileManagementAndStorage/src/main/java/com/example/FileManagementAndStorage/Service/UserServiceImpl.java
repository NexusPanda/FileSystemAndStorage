package com.example.FileManagementAndStorage.Service;

import com.example.FileManagementAndStorage.ModelDTO.UserDTO;
import com.example.FileManagementAndStorage.Repository.UserRepository;
import com.example.FileManagementAndStorage.Security.Request.LoginRequest;
import com.example.FileManagementAndStorage.Security.Request.SignUpRequest;
import com.example.FileManagementAndStorage.Security.Response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO register(SignUpRequest signup) {
        return null;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return null;
    }
}
