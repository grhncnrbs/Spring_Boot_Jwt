package com.grhncnrbs.SpringBootJwt.repository;

import com.grhncnrbs.SpringBootJwt.domain.Role;
import com.grhncnrbs.SpringBootJwt.domain.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional< Role> findByName (RoleType name);
}
