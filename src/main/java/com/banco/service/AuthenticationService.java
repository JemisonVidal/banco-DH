package com.banco.service;

import com.banco.model.JWTResponse;
import com.banco.model.LoginInfo;
import com.banco.model.User;

public interface AuthenticationService {

    JWTResponse authenticate(LoginInfo loginInfo) throws Exception;

    User save(User user);

}
