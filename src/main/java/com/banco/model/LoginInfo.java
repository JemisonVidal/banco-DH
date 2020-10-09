package com.banco.model;

import javax.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class LoginInfo {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
