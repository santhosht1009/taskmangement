package com.example.taskmangement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmangement.dto.Status;
import com.example.taskmangement.dto.Task;
import com.example.taskmangement.request.TaskRequest;
import com.example.taskmangement.response.ApiResponse;
import com.example.taskmangement.service.TaskService;

@RestController
@RequestMapping("/api/v1/users/task")
public class TaskController {

	@Autowired
	TaskService taskService;
	
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
	
}
