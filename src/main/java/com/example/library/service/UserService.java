package com.example.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.library.entity.User;
import com.example.library.exception.UserNotFoundException;
import com.example.library.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public User createUser(User user)throws UserNotFoundException {
		if(user!=null) {
			userRepository.save(user);
			return user;
		}
		throw new UserNotFoundException("Enter details correctly");
		
	}

	public User getUserById(long userId) throws UserNotFoundException {
		User user=userRepository.findById(userId).orElse(null);
		if(user!=null) {
			return user;
		}
		throw new UserNotFoundException("No User Exists with this userId : "+userId);
	}


	public User getUserByUserName(String userName)throws UserNotFoundException  {
		User user=userRepository.findByUserName(userName);
		if(user!=null) {
			return user;
		}
		throw new UserNotFoundException("No User Exists with this userName : "+userName);
	}

	public List<User> getAllUsers() {
		List<User> Users=userRepository.findAll();
		return Users;
	}

	public User updateUserById(User user, long userId) throws UserNotFoundException {
		User oldUser=getUserById(userId);

		if(oldUser!=null) {
			oldUser.setUserId(userId);
			oldUser.setUserName(user.getUserName());
			oldUser.setPassword(user.getPassword());
			oldUser.setRoles(user.getRoles());
			userRepository.save(oldUser);
			return oldUser;
		}
		throw new UserNotFoundException("No User Exists with this userId : "+userId + "to update");
	}

	public Boolean deleteUserById(long userId)throws UserNotFoundException {
		User user=getUserById(userId);
		if(user!=null) {
			userRepository.delete(user);
			return true;
		}
		throw new UserNotFoundException("No User Exists with this userId : "+userId + "to delete");
		
	}

	public Boolean deleteUserByName(String userName)throws UserNotFoundException {
		User user=getUserByUserName(userName);
		if(user!=null) {
			userRepository.delete(user);
			return true;
		}
		throw new UserNotFoundException("No User Exists with this userName : "+userName);
	}

}
