package com.taskmanager.taskmanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmanager.taskmanager.model.User;


public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUserName(String userName);

	User findById(Integer id);

	void deleteById(Integer id);
}
