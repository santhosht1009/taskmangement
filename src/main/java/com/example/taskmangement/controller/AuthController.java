
package com.example.taskmangement.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmangement.dto.User;
import com.example.taskmangement.exception.UserException;
import com.example.taskmangement.request.LoginRequest;
import com.example.taskmangement.response.AuthResponse;
import com.example.taskmangement.response.LoginResponse;
import com.example.taskmangement.service.AuthService;
import com.example.taskmangement.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthController {

	

	@Autowired
	AuthService authService;
	@Autowired
	UserService userService;
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> signup(@Valid @RequestBody User req) throws UserException{	

		return new ResponseEntity<AuthResponse>(authService.signup(req),HttpStatus.CREATED);
	}
	
	
	
	@PostMapping("/signin")
	public ResponseEntity<LoginResponse> signin(@RequestBody LoginRequest req) throws UserException{	

		return new ResponseEntity<LoginResponse>(authService.signin(req),HttpStatus.OK);
	}
	

	

	

@GetMapping("/")
public ResponseEntity<List<User>> getAllUsers() {
	List<User> users= userService.getAllUsers();
	return new ResponseEntity<List<User>>(users,HttpStatus.OK);
}

}
