package com.java.QueryBuilder.security;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.java.QueryBuilder.exceptions.DataNotFoundException;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (username.equals("Admin")) {
			return new User("Admin", "Password", new ArrayList<>());
		} else {
			throw new DataNotFoundException("User not Found");
		}

	}

}
