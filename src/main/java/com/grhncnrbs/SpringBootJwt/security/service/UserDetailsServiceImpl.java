package com.grhncnrbs.SpringBootJwt.security.service;

import com.grhncnrbs.SpringBootJwt.domain.User;
import com.grhncnrbs.SpringBootJwt.exception.message.ErrorMessage;
import com.grhncnrbs.SpringBootJwt.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, username)
        ));

        return UserDetailsImpl.create(user);
    }

    public UserDetailsImpl loadUserById(Long id) {
        User user = userRepository.findById(id).get();
        return UserDetailsImpl.create(user);
    }
}
