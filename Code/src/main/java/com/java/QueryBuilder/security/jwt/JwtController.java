package com.java.QueryBuilder.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.QueryBuilder.exceptions.DataNotFoundException;
import com.java.QueryBuilder.security.CustomUserDetailService;

@RestController
public class JwtController {

	@Autowired
	CustomUserDetailService customUserDetailService;

	@Autowired
	JwtHelper jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/token")
	public String generateToken(@RequestParam String username, @RequestParam String password) {

		System.out.println(username);
		System.out.println(password);

		try {

			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataNotFoundException("Invalid Username or Password");
		}

		UserDetails userDetails = this.customUserDetailService.loadUserByUsername(username);

		String token = this.jwtUtil.generateToken(userDetails);

		System.out.println("JWT Token :" + token);

		return "JWT Token :" + token;
	}
}
