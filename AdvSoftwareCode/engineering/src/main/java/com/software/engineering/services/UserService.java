package com.software.engineering.services;

import com.software.engineering.bo.request.SignInRequest;
import com.software.engineering.bo.request.SignUpRequest;
import com.software.engineering.bo.response.SignInResponse;
import com.software.engineering.bo.response.SignUpResponse;

public interface UserService {

    SignUpResponse signUp(SignUpRequest request);

    SignInResponse signIn(SignInRequest request);
}
