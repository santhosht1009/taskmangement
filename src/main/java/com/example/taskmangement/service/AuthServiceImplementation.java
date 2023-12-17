package com.example.taskmangement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.taskmangement.dto.Role;
import com.example.taskmangement.dto.User;
import com.example.taskmangement.exception.ResourceNotFoundException;
import com.example.taskmangement.exception.UserException;

import com.example.taskmangement.repository.UserRepository;
import com.example.taskmangement.request.LoginRequest;
import com.example.taskmangement.response.ApiResponse;
import com.example.taskmangement.response.AuthResponse;
import com.example.taskmangement.response.LoginResponse;
import com.example.taskmangement.security.JwtService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService{

	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final JwtService jwtService;
	
	private final AuthenticationManager authenticationManager;
	
	
	
	public AuthResponse signup(User registerRequest ) {
		Optional<User> isEmailExist=userRepository.findByEmail(registerRequest.getEmail());

		if(isEmailExist.isPresent()) {
			throw new UserException("Email is Already used with other Account");
			
		}
		
		
		registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword())   );
	
		registerRequest.setRole(Role.USER);
		
		User savedUser=userRepository.save(registerRequest);
		AuthResponse authResponse=new AuthResponse();
//		
//	var jwt=jwtService.generateToken(user);
//	var rjwt=jwtService.generateRefreshToken(new HashMap<String, Object>(), user);
//		authResponse.setJwt(jwt);
//		authResponse.setRefreshToken(rjwt);
		savedUser.setPassword(null);
		authResponse.setUser(savedUser);
		authResponse.setMessage("SignUp Success");
		return authResponse;
	}

	@Override
	public LoginResponse signin(LoginRequest loginRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		
		User user=userRepository.findByEmail(loginRequest.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
		var jwt=jwtService.generateToken(user);
//		var rjwt=jwtService.generateRefreshToken(new HashMap<String, Object>(), user);
		LoginResponse loginResponse=new LoginResponse();
		loginResponse.setJwt(jwt);
		loginResponse.setEmail(user.getEmail());
		loginResponse.setId(user.getId());
		loginResponse.setName(user.getName());
		loginResponse.setRole(user.getRole());
	
		return loginResponse;
	}

	@Override
	public ApiResponse updatePassword(Integer userId, String password) {
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);
		return new ApiResponse("Password Updated Successfully", true);
	}


}
