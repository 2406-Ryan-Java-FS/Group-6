package com.group6.revature.repository;

import com.group6.revature.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepo extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);
    Users findByEmail(String email);
}
