package com.project.MyProject.repository;

import com.project.MyProject.dbo.UserDbo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDbo, Long> {
    UserDbo findByUsername(String username);

    UserDbo findByActivationCode(String code);
}
