package com.jasche.notetoself.repository;

import com.jasche.notetoself.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}