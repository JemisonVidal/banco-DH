package com.banco.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.dto.UserDTO;
import com.banco.model.JWTResponse;
import com.banco.model.LoginInfo;
import com.banco.model.User;
import com.banco.service.AuthenticationService;

@RestController
@RequestMapping("/v1/api")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public ResponseEntity<User> save(@RequestBody @Valid UserDTO userDTO) {
		final User user = authenticationService.save(userDTO.toUser(passwordEncoder));
		if (user == null) {
			return ResponseEntity.badRequest().body(null);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<JWTResponse> authenticate(@RequestBody @Valid LoginInfo loginInfo) throws Exception {
		final JWTResponse jwtResponse = authenticationService.authenticate(loginInfo);
		return ResponseEntity.ok(jwtResponse);
	}

}
