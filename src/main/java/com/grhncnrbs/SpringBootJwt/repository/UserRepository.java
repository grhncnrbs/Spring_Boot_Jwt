package com.grhncnrbs.SpringBootJwt.repository;

import com.grhncnrbs.SpringBootJwt.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUserName (String username);
    Boolean existsByEmail (String email);
    Optional <User> findByUserName(String username);
    Optional <User> findByEmail(String username);

}
