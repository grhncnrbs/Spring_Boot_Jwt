package com.grhncnrbs.SpringBootJwt.controller;

import com.grhncnrbs.SpringBootJwt.dto.request.RegisterRequest;
import com.grhncnrbs.SpringBootJwt.dto.response.AppResponse;
import com.grhncnrbs.SpringBootJwt.dto.response.ResponseMessage;
import com.grhncnrbs.SpringBootJwt.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserJwtController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AppResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest);
        AppResponse appResponse = new AppResponse();
        appResponse.setMessage(ResponseMessage.REGISTER_RESPONSE_MESSAGE);
        appResponse.setSuccess(true);

        return new ResponseEntity<>(appResponse, HttpStatus.CREATED);
    }

}
