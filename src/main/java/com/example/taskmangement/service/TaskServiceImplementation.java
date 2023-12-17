package com.example.taskmangement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taskmangement.dto.Status;
import com.example.taskmangement.dto.Task;
import com.example.taskmangement.exception.ResourceNotFoundException;
import com.example.taskmangement.repository.TaskRepo;
import com.example.taskmangement.request.TaskRequest;

@Service
public class TaskServiceImplementation implements TaskService{

	@Autowired
	TaskRepo taskRepo;
	
	@Override
	public Task createTask(Task task) {

		return taskRepo.save(task);
	}

	@Override
	public List<Task> getTasks() {
		
		return taskRepo.findAll();
	}

	@Override
	public List<Task> getTaskByUser(Integer userId) {
		
		return taskRepo.findTaskByUserId(userId);
	}

	@Override
	public Task getTaskById(Integer taskId) {
		// TODO Auto-generated method stub
		return taskRepo.findById(taskId).orElseThrow(()->new ResourceNotFoundException("Task","Id",taskId));
	}

	@Override
	public Task updateTask(TaskRequest req) {
Task task=getTaskById(req.getId());
		if(req.getStatus()!=null && req.getStatus().toLowerCase().equals("inprogress"))
		task.setStatus(Status.INPROGRESS);
		else if(req.getStatus()!=null && req.getStatus().toLowerCase().equals("completed") )
			task.setStatus(Status.COMPLETED);
			
			task.setDescription(req.getDescription());
		task.setTitle(req.getTitle());
		task.setDue_date(req.getDue_date());
		
	
		return	taskRepo.save(task);
	}

}
