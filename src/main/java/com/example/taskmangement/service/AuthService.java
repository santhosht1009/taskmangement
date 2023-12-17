package com.example.taskmangement.service;

import com.example.taskmangement.dto.User;
import com.example.taskmangement.request.LoginRequest;
import com.example.taskmangement.response.ApiResponse;
import com.example.taskmangement.response.AuthResponse;
import com.example.taskmangement.response.LoginResponse;

public interface AuthService {
	AuthResponse signup(User registerRequest);
	LoginResponse signin(LoginRequest loginRequest);
	ApiResponse updatePassword(Integer userId,String password);
	
}
