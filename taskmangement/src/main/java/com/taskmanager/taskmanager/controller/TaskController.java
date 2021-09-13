package com.taskmanager.taskmanager.controller;

import static com.taskmanager.taskmanager.constant.TaskManagerApiConstants.CONTENT_TYPE_APPLICATION_JSON_UTF_8;
import static com.taskmanager.taskmanager.constant.TaskManagerApiConstants.ERROR_INVALID_REQUEST_BODY_APPLICATION_EXCEPTION;
import static com.taskmanager.taskmanager.constant.TaskManagerApiConstants.ERROR_INVALID_TASK;
import static com.taskmanager.taskmanager.constant.TaskManagerApiConstants.HEADER_CONTENT_TYPE;
import static com.taskmanager.taskmanager.constant.TaskManagerApiConstants.KEY_TASK_ID;
import static com.taskmanager.taskmanager.constant.TaskManagerApiConstants.MSG_INSERT_SUCCESS;
import static com.taskmanager.taskmanager.constant.TaskManagerApiConstants.MSG_UPDATE_SUCCESS;
import static com.taskmanager.taskmanager.constant.TaskManagerApiConstants.SUCCESS_OPERATION;
import static com.taskmanager.taskmanager.constant.TaskManagerApiConstants.SUCCESS_RESPONSE_TEMPLATE;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskmanager.taskmanager.exception.Error;
import com.taskmanager.taskmanager.exception.ApplicationException;
import com.taskmanager.taskmanager.model.StatusEnum;
import com.taskmanager.taskmanager.model.Task;
import com.taskmanager.taskmanager.service.impl.TaskService;

import lombok.var;



@RestController
public class TaskController {

	@Autowired
	private TaskService taskService;

	/* Create task */
	@PostMapping("/createTask")
	public ResponseEntity<?> add(@RequestBody(required = false) String body) {

		Task task = taskService.prepareTask(body);
		taskService.save(task);
		return ResponseEntity.status(HttpStatus.OK)
				.header(HEADER_CONTENT_TYPE, CONTENT_TYPE_APPLICATION_JSON_UTF_8)
				.body(MessageFormat.format(SUCCESS_RESPONSE_TEMPLATE, SUCCESS_OPERATION, MSG_INSERT_SUCCESS));
	}

	/* Edit task. Closed tasks cannot be edited */
	@SuppressWarnings("unchecked")
	@PutMapping("/task")
	public ResponseEntity<?> update(@RequestBody(required = false) String body) {

		Task task = taskService.prepareTask(body);
		Map<String, Object> requestMap = null;
		try {
			final ObjectMapper mapper = new ObjectMapper();
			requestMap = (Map<String, Object>) mapper.readValue(body, Map.class);
		} catch (IOException e) {
			throw new ApplicationException(new Error(ERROR_INVALID_REQUEST_BODY_APPLICATION_EXCEPTION));
		}
		Integer id = (Integer) requestMap.get(KEY_TASK_ID);
		Task existingTask = taskService.get(id);
		if(!Objects.nonNull(existingTask)) {
			throw new ApplicationException(new Error(ERROR_INVALID_REQUEST_BODY_APPLICATION_EXCEPTION));
		}
		String status = existingTask.getStatus().toLowerCase();
		if(status.equals(StatusEnum.CLOSED.getValue())) {
			throw new ApplicationException(new Error(ERROR_INVALID_TASK));
		}
		if(Objects.nonNull(id)) {
			task.setTaskId(id);
		}else {
			throw new ApplicationException(new Error(ERROR_INVALID_REQUEST_BODY_APPLICATION_EXCEPTION));
		}
		taskService.save(task);
		return ResponseEntity.status(HttpStatus.OK)
				.header(HEADER_CONTENT_TYPE, CONTENT_TYPE_APPLICATION_JSON_UTF_8)
				.body(MessageFormat.format(SUCCESS_RESPONSE_TEMPLATE, SUCCESS_OPERATION, MSG_UPDATE_SUCCESS));
	}

	/* Get task */
	@GetMapping("/task/{id}")
	public ResponseEntity<Task> get(@PathVariable Integer id) {
		try {
			Task task = taskService.get(id);
			return new ResponseEntity<Task>(task, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Task>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/tasks")
	public List<Task> list() {
		return taskService.listAll();
	}

	/* Get all tasks by user. Only accessible to ADMIN */
	@GetMapping("/tasks/user/{id}")
	public List<Task> getAllTasksByUser(@PathVariable Integer id) {
		return taskService.listByUser(id);
	}

	/* Get all tasks by project */
	@GetMapping("/tasks/project/{id}")
	public List<Task> getAllTasksByProject(@PathVariable Integer id) {
		return taskService.listByProject(id);
	}

	/* Get all expired task */
	@GetMapping("/tasks/expired")
	public List<Task> getAllTasksByDueDateExpired() {
		return taskService.listByDueDateExpired();
	}

	/* Get all tasks by status */
	@GetMapping("/tasks/status/{status}")
	public List<Task> getAllTasksByProject(@PathVariable String status) {
		return taskService.listByStatus(status);
	}

	@DeleteMapping("/tasks/{id}")
	public void delete(@PathVariable Integer id) {
		taskService.delete(id);
	}
}