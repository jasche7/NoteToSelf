package com.jasche.notetoself.repository;

import com.jasche.notetoself.domain.Author;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByName(String name);
}