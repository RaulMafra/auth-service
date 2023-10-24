package com.api.service;

import com.api.model.User;
import com.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder encoder;
	
	public User saveUser(User user) {
		String password = user.getPassword();
		user.setPassword(encoder.encode(password));
		return userRepository.save(user);
	}

}
