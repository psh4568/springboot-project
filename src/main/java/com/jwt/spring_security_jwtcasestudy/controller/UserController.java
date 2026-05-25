package com.jwt.spring_security_jwtcasestudy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.spring_security_jwtcasestudy.dto.ProductResponseDTO;
import com.jwt.spring_security_jwtcasestudy.dto.UserRequestDTO;
import com.jwt.spring_security_jwtcasestudy.dto.UserResponseDTO;
import com.jwt.spring_security_jwtcasestudy.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody UserRequestDTO request) {

		String response = userService.register(request);
		System.out.print("response is here " + response);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
		List<UserResponseDTO> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}

}