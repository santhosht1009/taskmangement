package com.example.taskmangement.response;

import java.util.Set;

import com.example.taskmangement.dto.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
private Integer id;
private String name;
private String email;
private Role role;
private String jwt;
	
	
}
