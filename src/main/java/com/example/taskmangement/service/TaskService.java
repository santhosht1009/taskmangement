package com.example.taskmangement.service;

import java.util.List;

import com.example.taskmangement.dto.Task;
import com.example.taskmangement.request.TaskRequest;
import com.example.taskmangement.response.TaskResponse;

public interface TaskService {

	public Task createTask(TaskRequest req);
	
	public TaskResponse getTasks(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
	
	public List<Task> getTaskByUser(Integer userId);
	
	public Task getTaskById(Integer taskId);
	
	public Task updateTask(TaskRequest req);
}
