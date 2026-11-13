package com.example.noteapi.auth;

import com.example.noteapi.dto.LoginRequest;
import com.example.noteapi.dto.LoginResponse;
import com.example.noteapi.exception.LoginArgumentNotValidException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponse authentication(LoginRequest loginRequest) {

//        usernameが空でないかのチェック
        if (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty()) {
            throw new LoginArgumentNotValidException("username is empty");
        }

//        passwordが空でないかのチェック
        if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            throw new LoginArgumentNotValidException("password is empty");
        }

//        認証
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            loginRequest.getUsername(), loginRequest.getPassword())
        );

//        JWTトークンを生成
        String token = jwtService.generateToken(loginRequest.getUsername());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);

        return loginResponse;
    }
}
