package com.example.taskmangement.response;



import com.example.taskmangement.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

private String jwt;
private String refreshToken;
private User user;
private String message;

}
