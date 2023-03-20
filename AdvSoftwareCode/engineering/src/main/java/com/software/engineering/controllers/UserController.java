package com.software.engineering.controllers;

import com.software.engineering.bo.request.SignInRequest;
import com.software.engineering.bo.request.SignUpRequest;
import com.software.engineering.bo.response.SignInResponse;
import com.software.engineering.bo.response.SignUpResponse;
import com.software.engineering.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/signup")
    public ResponseEntity<SignUpResponse> signup(@RequestBody SignUpRequest request){
        return new ResponseEntity<>(userService.signUp(request), HttpStatus.OK);
    }

    @PostMapping("/signIn")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest request){
        return new ResponseEntity<>(userService.signIn(request), HttpStatus.OK);
    }
}
