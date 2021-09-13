package com.taskmanager.taskmanager.service.impl;

import static com.taskmanager.taskmanager.constant.TaskManagerApiConstants.DATE_FORMAT;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskmanager.taskmanager.model.Project;
import com.taskmanager.taskmanager.model.Task;
import com.taskmanager.taskmanager.model.User;
import com.taskmanager.taskmanager.repository.ProjectRepository;
import com.taskmanager.taskmanager.repository.UserRepository;



@Service
@Transactional
public class DataPreparationService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private TaskService taskService;

	public void prepareData() {
		User user1 = new User();
		user1.setUserName("admin");
		user1.setPassword(new BCryptPasswordEncoder().encode("admin"));
		user1.setRole("admin");
		userRepository.saveAndFlush(user1);

		User user2 = new User();
		user2.setUserName("user1");
		user2.setPassword(new BCryptPasswordEncoder().encode("user1"));
		user2.setRole("user");
		userRepository.saveAndFlush(user2);

		Project project1 = new Project();
		project1.setName("Covid-19");
		project1.setOwner(user1);
		projectRepository.saveAndFlush(project1);

		Project project2 = new Project();
		project2.setName("Dangu fever");
		project2.setOwner(user2);
		projectRepository.saveAndFlush(project2);

		Task task1 = new Task();
		task1.setDescription("Inform the people about Covid-19");
		task1.setStatus("Open");
		task1.setProject(project1);
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		Date date1 = null;
		try {
			date1 = formatter.parse("2021-01-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		task1.setDueDate(date1);
		task1.setAssignee(user1);
		taskService.save(task1);

		Task task2 = new Task();
		task2.setDescription("Confirm Quarantine People");
		task2.setStatus("InProgress");
		task2.setProject(project1);
		Date date2 = null;
		try {
			date2 = formatter.parse("2021-07-31");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		task2.setDueDate(date2);
		task2.setAssignee(user1);
		taskService.save(task2);

		Task task3 = new Task();
		task3.setDescription("Vaccination");
		task3.setStatus("InProgress");
		task3.setProject(project2);
		Date date3 = null;
		try {
			date3 = formatter.parse("2021-01-31");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		task3.setDueDate(date3);
		task3.setAssignee(user1);
		taskService.save(task3);

		Task task4 = new Task();
		task4.setDescription("Good Treat to the Infected People");
		task4.setStatus("Closed");
		task4.setProject(project2);
		Date date4 = null;
		try {
			date4 = formatter.parse("2021-09-30");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		task4.setDueDate(date4);
		task4.setAssignee(user2);
		taskService.save(task4);
	}
}