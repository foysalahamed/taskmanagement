package com.taskmanager.taskmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.taskmanager.taskmanager.service.impl.DataPreparationService;

@SpringBootApplication

public class TaskManagerApplication implements CommandLineRunner{

	@Autowired
	private DataPreparationService dataPreparationService;

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		dataPreparationService.prepareData();
		
	}
}
