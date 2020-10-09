package com.banco.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.banco.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDTO {

	@NotBlank
	@Email(message = "Fill in a valid email")
	private final String email;	

	@NotBlank(message = "Mandatory Filling")
	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$", message = "The password must be 1 uppercase, 1 lowercase, and 1 digit. At least 8 characters.")
	private final String password;

	public User toUser(PasswordEncoder passwordEncoder) {
		return User.builder().email(this.email).password(passwordEncoder.encode(this.password)).build();
	}

}
