package com.ydt.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ydt.entity.User;
import com.ydt.payload.JwtAuthenticationResponse;
import com.ydt.payload.LoginRequest;
import com.ydt.repository.UserRepository;

@RestController
@RequestMapping("/test")
public class TestController {
	@Autowired
    UserRepository userRepository;

	@GetMapping("/he")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> test() {
		List<User> entityList = userRepository.findAll();

		return new ResponseEntity<Object>(entityList, HttpStatus.OK);
    }

}
