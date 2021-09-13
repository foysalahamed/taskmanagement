package com.taskmanager.taskmanager.repository;
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmanager.taskmanager.model.Project;


public interface ProjectRepository extends JpaRepository<Project, Integer> {
	public List<Project> findByOwnerId(Integer id);
	Project findByProjectId(Integer projectId);
	public void deleteByProjectId(Integer projectId);
}