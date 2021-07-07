package com.jasche.notetoself.repository;

import com.jasche.notetoself.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for storing User objects, identified by String id.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
}