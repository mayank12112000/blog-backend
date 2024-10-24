package com.mayank.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mayank.blog.entities.User;
import com.mayank.blog.payloads.UserDto;
import com.mayank.blog.repositories.UserRepo;
import com.mayank.blog.services.UserService;
import com.mayank.blog.exceptions.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = dtoToUser(userDto);
		User savedUser = userRepo.save(user);
		return UserToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User userToUpdate = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User"," id",userId));
		userToUpdate.setAbout(userToUpdate.getAbout());
		userToUpdate.setEmail(userToUpdate.getEmail());
		userToUpdate.setName(userToUpdate.getName());
		userToUpdate.setPassword(userToUpdate.getPassword());
		User updatedUser = userRepo.save(userToUpdate);
		UserDto userDto1 = this.UserToDto(updatedUser);
		
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", " id", userId));
		return this.UserToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> userList = userRepo.findAll();
		List<UserDto> userDtos = userList.stream().map(user->this.UserToDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", " id", userId));
		userRepo.delete(user);
	}
	
	private User dtoToUser(UserDto userDto) {
		User user = modelMapper.map(userDto, User.class);
		return user;
	}
	private UserDto UserToDto(User user) {
		UserDto userDto = modelMapper.map(user,UserDto.class);
		return userDto;
	}

}
