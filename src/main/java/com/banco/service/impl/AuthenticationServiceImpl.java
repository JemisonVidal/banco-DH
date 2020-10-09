package com.banco.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.banco.model.JWTResponse;
import com.banco.model.LoginInfo;
import com.banco.model.User;
import com.banco.repository.UserRepository;
import com.banco.security.JWTTokenUtil;
import com.banco.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	public JWTResponse authenticate(LoginInfo loginInfo) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginInfo.getEmail(), loginInfo.getPassword()));
			final UserDetails userDetails = userDetailsService.loadUserByUsername(loginInfo.getEmail());
			final String token = jwtTokenUtil.generateToken(userDetails);
			final User user = userRepository.findByEmail(loginInfo.getEmail()).get();
			return new JWTResponse(user, token);
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	@Override
	public User save(User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			return null;
		}
		return userRepository.save(user);
	}
}
