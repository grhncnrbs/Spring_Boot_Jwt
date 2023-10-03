package com.grhncnrbs.SpringBootJwt.service;

import com.grhncnrbs.SpringBootJwt.domain.Role;
import com.grhncnrbs.SpringBootJwt.domain.User;
import com.grhncnrbs.SpringBootJwt.domain.enums.RoleType;
import com.grhncnrbs.SpringBootJwt.dto.request.RegisterRequest;
import com.grhncnrbs.SpringBootJwt.exception.ConflictException;
import com.grhncnrbs.SpringBootJwt.exception.ResourceNotFoundException;
import com.grhncnrbs.SpringBootJwt.exception.message.ErrorMessage;
import com.grhncnrbs.SpringBootJwt.repository.RoleRepository;
import com.grhncnrbs.SpringBootJwt.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    public void register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST, registerRequest.getEmail()));
        }
        if (userRepository.existsByEmail(registerRequest.getUsername())) {
            throw new ConflictException(String.format(ErrorMessage.USERNAME_ALREADY_EXIST, registerRequest.getUsername()));
        }

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        Role role = roleRepository.findByName(RoleType.CUSTOMER).
                orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessage.ROLE_NOT_FOUND_MESSAGE, RoleType.CUSTOMER.name())));

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setUsername(registerRequest.getUsername());
        user.setAddress(registerRequest.getAddress());
        user.setPhone(registerRequest.getPhone());
        user.setBirthDate(registerRequest.getBirthDate());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setLocation(registerRequest.getLocation());
        user.setCreateDate(LocalDateTime.now());
        user.setBuiltIn(false);
        user.setRoles(roles);

        roleRepository.save(role);
        userRepository.save(user);
    }

    public User getOneUserByUsername(String username){
        User user =userRepository.findByUserName(username).
                orElseThrow(() -> new UsernameNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE,username)));
        return user;
    }
}
