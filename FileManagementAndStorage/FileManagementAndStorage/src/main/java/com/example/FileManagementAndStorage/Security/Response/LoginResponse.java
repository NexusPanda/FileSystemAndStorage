package com.example.FileManagementAndStorage.Security.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

    private Long id;

    private String username;

    private String email;

    private String role;

    private String jwtToken;
}
