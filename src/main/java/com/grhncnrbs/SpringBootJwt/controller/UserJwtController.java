package com.grhncnrbs.SpringBootJwt.controller;

import com.grhncnrbs.SpringBootJwt.domain.User;
import com.grhncnrbs.SpringBootJwt.dto.request.LoginRequest;
import com.grhncnrbs.SpringBootJwt.dto.request.RegisterRequest;
import com.grhncnrbs.SpringBootJwt.dto.response.AppResponse;
import com.grhncnrbs.SpringBootJwt.dto.response.LoginResponse;
import com.grhncnrbs.SpringBootJwt.dto.response.ResponseMessage;
import com.grhncnrbs.SpringBootJwt.security.jwt.JwtTokenProvider;
import com.grhncnrbs.SpringBootJwt.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserJwtController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<AppResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest);
        AppResponse appResponse = new AppResponse();
        appResponse.setMessage(ResponseMessage.REGISTER_RESPONSE_MESSAGE);
        appResponse.setSuccess(true);

        return new ResponseEntity<>(appResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);
        SecurityContextHolder.getContext().setAuthentication(auth);
        User user = userService.getOneUserByUsername(loginRequest.getUsername());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(jwtToken);
        loginResponse.setUserId(user.getId());
        loginResponse.setMessage(user.getUsername() + "take your token");
        return new ResponseEntity<>(loginResponse, HttpStatus.CREATED);
    }

}
