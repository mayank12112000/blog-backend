package com.mayank.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mayank.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	
}
