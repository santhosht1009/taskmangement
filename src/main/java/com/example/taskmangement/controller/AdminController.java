package com.example.taskmangement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmangement.config.AppConstants;
import com.example.taskmangement.dto.Status;
import com.example.taskmangement.dto.Task;
import com.example.taskmangement.dto.User;
import com.example.taskmangement.request.TaskRequest;
import com.example.taskmangement.response.ApiResponse;
import com.example.taskmangement.response.TaskResponse;
import com.example.taskmangement.service.TaskService;
import com.example.taskmangement.service.UserService;
@RestController
@RequestMapping("/api/v1/admin/")
public class AdminController {

	
	@Autowired
	TaskService taskService;
	
	@Autowired
	UserService userService;
	
	
	@GetMapping("/")
	public ResponseEntity<TaskResponse> getAllTasks(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
			){
		return new ResponseEntity<TaskResponse>(taskService.getTasks(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
	}
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Task>> getTaskByUserId(@PathVariable Integer userId){
		return new ResponseEntity<List<Task>>(taskService.getTaskByUser(userId),HttpStatus.OK);
	}
	@GetMapping("/{taskId}")
	public ResponseEntity<Task> getTaskByTaskId(@PathVariable Integer taskId){
		return new ResponseEntity<Task>(taskService.getTaskById(taskId),HttpStatus.OK);
	}
	
	@PutMapping("/")
	public ResponseEntity<ApiResponse> updateTask(@RequestBody TaskRequest req){
		
		taskService.updateTask(req);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Task Updated Successfully", true) ,HttpStatus.CREATED);
	}
	
	@PostMapping("/")
	public ResponseEntity<ApiResponse> createTask(@RequestBody TaskRequest req){
	
		taskService.createTask(req);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Task Created Successfully", true) ,HttpStatus.OK);
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users= userService.getAllUsers();
		return new ResponseEntity<List<User>>(users,HttpStatus.CREATED);
	}
	
}
