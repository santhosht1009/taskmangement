package com.example.taskmangement.service;

import java.util.List;

import com.example.taskmangement.dto.User;

import jakarta.validation.Valid;

public interface UserService {
	
	public User createUser(User users);
	
	public User updateUser(User users,Integer userId);
	
	public User getUserById(Integer userId);
	
	public List<User> getAllUsers();
	
	 
	
	public void deleteUser(Integer userId);
	User registerNewUser(@Valid User userDto);
//	
//	UserDetailsService userDetailsService();
}
