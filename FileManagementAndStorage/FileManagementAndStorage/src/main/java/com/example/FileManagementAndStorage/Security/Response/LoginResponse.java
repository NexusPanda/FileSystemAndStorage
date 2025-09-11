package com.example.FileManagementAndStorage.Security.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private Long id;

    private String username;

    private String jwtToken;

    private List<String> roles;
}
