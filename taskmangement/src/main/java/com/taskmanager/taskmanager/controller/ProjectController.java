package com.taskmanager.taskmanager.controller;

import static com.taskmanager.taskmanager.constant.TaskManagerApiConstants.CONTENT_TYPE_APPLICATION_JSON_UTF_8;
import static com.taskmanager.taskmanager.constant.TaskManagerApiConstants.HEADER_CONTENT_TYPE;
import static com.taskmanager.taskmanager.constant.TaskManagerApiConstants.MSG_INSERT_SUCCESS;
import static com.taskmanager.taskmanager.constant.TaskManagerApiConstants.MSG_UPDATE_SUCCESS;
import static com.taskmanager.taskmanager.constant.TaskManagerApiConstants.SUCCESS_OPERATION;
import static com.taskmanager.taskmanager.constant.TaskManagerApiConstants.SUCCESS_RESPONSE_TEMPLATE;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanager.taskmanager.model.Project;
import com.taskmanager.taskmanager.service.impl.ProjectService;


@RestController
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	/* Create project */
	@PostMapping("/project")
	public ResponseEntity<?> add(@RequestBody Project project) {
		projectService.save(project);
		return ResponseEntity.status(HttpStatus.OK)
				.header(HEADER_CONTENT_TYPE, CONTENT_TYPE_APPLICATION_JSON_UTF_8)
				.body(MessageFormat.format(SUCCESS_RESPONSE_TEMPLATE, SUCCESS_OPERATION, MSG_INSERT_SUCCESS));
	}

	/* Get all projects */
	@GetMapping("/projects")
	public List<Project> list() {
		return projectService.listAll();
	}

	/* Delete Project */
	@DeleteMapping("/project/{id}")
	public void delete(@PathVariable Integer id) {
		projectService.delete(id);
	}

	/* Get all projects by user. Only accessible to ADMIN */
	@GetMapping("/projects/user/{id}")
	public List<Project> getAllProjectsByUser(@PathVariable Integer id) {
		return projectService.listByUser(id);
	}

	/* Update project */
	@PutMapping("/project/{id}")
	public ResponseEntity<?> update(@RequestBody Project project, @PathVariable Integer id) {
		projectService.save(project);
		return ResponseEntity.status(HttpStatus.OK)
				.header(HEADER_CONTENT_TYPE, CONTENT_TYPE_APPLICATION_JSON_UTF_8)
				.body(MessageFormat.format(SUCCESS_RESPONSE_TEMPLATE, SUCCESS_OPERATION, MSG_UPDATE_SUCCESS));
	}

}