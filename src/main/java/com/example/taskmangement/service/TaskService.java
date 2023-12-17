package com.example.taskmangement.service;

import java.util.List;

import com.example.taskmangement.dto.Task;
import com.example.taskmangement.request.TaskRequest;

public interface TaskService {

	public Task createTask(Task task);
	
	public List<Task> getTasks();
	
	public List<Task> getTaskByUser(Integer userId);
	
	public Task getTaskById(Integer taskId);
	
	public Task updateTask(TaskRequest req);
}
