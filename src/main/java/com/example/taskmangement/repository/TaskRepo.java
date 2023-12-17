package com.example.taskmangement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskmangement.dto.Task;

public interface TaskRepo extends JpaRepository<Task, Integer>{

	public List<Task> findTaskByUserId(Integer userId);
	
}
