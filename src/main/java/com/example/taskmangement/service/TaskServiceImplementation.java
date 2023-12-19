package com.example.taskmangement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import com.example.taskmangement.dto.Status;
import com.example.taskmangement.dto.Task;
import com.example.taskmangement.exception.ResourceNotFoundException;
import com.example.taskmangement.exception.UserException;
import com.example.taskmangement.repository.TaskRepo;
import com.example.taskmangement.request.TaskRequest;
import com.example.taskmangement.response.TaskResponse;

@Service
public class TaskServiceImplementation implements TaskService{

	@Autowired
	TaskRepo taskRepo;
	
	@Override
	public Task createTask(TaskRequest req) {

		Task task=new Task();
		task.setStatus(Status.PENDING);
		task.setDescription(req.getDescription());
		task.setTitle(req.getTitle());
		task.setDue_date(req.getDue_date());
		task.setUserId(req.getUserId());
		
		
		return taskRepo.save(task);
	}

	@Override
	public TaskResponse getTasks(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
			
		}else
		{
			sort=Sort.by(sortBy).descending();
		}
		
	Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
		
		Page<Task> pageTask=taskRepo.findAll(pageable);
		List<Task> tasks=pageTask.getContent();
		
		TaskResponse taskResponse=new TaskResponse();
		taskResponse.setTasks(tasks);
		taskResponse.setPageNumber(pageTask.getNumber());
		taskResponse.setPageSize(pageTask.getSize());
		taskResponse.setTotalElements(pageTask.getTotalElements());
		taskResponse.setTotalPages(pageTask.getTotalPages());
		taskResponse.setLastPage(pageTask.isLast());
		
		
		return taskResponse;
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
//System.out.println(task);
if(task.getUserId()==req.getUserId())
{

		if(!req.getStatus().equals("") && req.getStatus().toLowerCase().equals("inprogress"))
		task.setStatus(Status.INPROGRESS);
		else if(!req.getStatus().equals("") && req.getStatus().toLowerCase().equals("completed") )
			task.setStatus(Status.COMPLETED);
			
		if(!req.getDescription().equals("")) {
			task.setDescription(req.getDescription());}
		
		if(!req.getTitle().equals("")) {
		task.setTitle(req.getTitle());}
		if(req.getDue_date()!=null) {
		task.setDue_date(req.getDue_date());}
		
	
		return	taskRepo.save(task);
		
}
else
{
	throw new UserException("you cannot edit other user task");
}


	}

}
