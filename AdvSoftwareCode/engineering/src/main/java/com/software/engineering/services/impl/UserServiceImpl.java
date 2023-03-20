package com.software.engineering.services.impl;

import com.software.engineering.bo.request.SignInRequest;
import com.software.engineering.bo.request.SignUpRequest;
import com.software.engineering.bo.response.SignInResponse;
import com.software.engineering.bo.response.SignUpResponse;
import com.software.engineering.entities.UserEntity;
import com.software.engineering.entities.UserRoles;
import com.software.engineering.repositories.UserRepository;
import com.software.engineering.services.UserService;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import static java.util.Objects.isNull;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public SignUpResponse signUp(SignUpRequest request) {
        preValidateSignup(request);

        UserEntity user = userRepository.save(mapSignupRequestToEntity(request));
        return SignUpResponse.builder()
                .id(user.getId())
                .username(user.getUserName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }


    @Override
    public SignInResponse signIn(SignInRequest request) {
        preValidateSignIn(request);

        UserEntity userEntity = userRepository.findOne(Example.of(UserEntity.builder().email(request.getEmail()).build()))
                .orElseThrow(() -> new RuntimeException("Email not found!"));

        if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword()))
            throw new RuntimeException("Incorrect Password");

        return SignInResponse.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .username(userEntity.getUserName())
                .role(userEntity.getRole())
                .build();
    }

    private void preValidateSignup(SignUpRequest request) {

        if (isNull(request.getUsername()) || isNull(request.getEmail()) || isNull(request.getPassword()))
            throw new RuntimeException("Please Enter All Fields!");

        if (userRepository.exists(Example.of(UserEntity.builder().email(request.getEmail()).build())))
            throw new RuntimeException("Email Already Exists!");
    }

    private UserEntity mapSignupRequestToEntity(SignUpRequest request) {
        return UserEntity.builder()
                .userName(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .creationDate(new Timestamp(System.currentTimeMillis()))
                .role(UserRoles.USER)
                .build();
    }

    private void preValidateSignIn(SignInRequest request) {

        if (isNull(request.getEmail()) || isNull(request.getPassword()))
            throw new RuntimeException("Please Enter All Fields!");
    }
}
