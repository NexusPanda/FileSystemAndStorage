package com.example.FileManagementAndStorage.Service;

import com.example.FileManagementAndStorage.Model.UserEntity;
import com.example.FileManagementAndStorage.ModelDTO.UserDTO;
import com.example.FileManagementAndStorage.Repository.UserRepository;
import com.example.FileManagementAndStorage.Security.Jwt.JwtUtils;
import com.example.FileManagementAndStorage.Security.Request.LoginRequest;
import com.example.FileManagementAndStorage.Security.Request.SignUpRequest;
import com.example.FileManagementAndStorage.Security.Response.LoginResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public UserDTO register(SignUpRequest signup) {
        if (userRepository.findByUsername(signup.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken!");
        }
        if (userRepository.findByEmail(signup.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered!");
        }
        UserEntity user = modelMapper.map(signup, UserEntity.class);
        String pass = passwordEncoder.encode(user.getPassword());
        user.setPassword(pass);
        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }


    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        UserEntity user = (UserEntity) userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with username: " + loginRequest.getEmail()));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }
        String jwtToken = jwtUtils.getTokenFromUserName((UserDetails) user);
        return LoginResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .jwtToken(jwtToken)
                .build();
    }

}
