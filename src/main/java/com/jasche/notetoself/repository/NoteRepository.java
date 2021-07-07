package com.jasche.notetoself.repository;

import com.jasche.notetoself.domain.Note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for storing Note objects, identified by Long id.
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    
    /**
     * Spring Data JPA findAllById() returns all Notes belonging to the User with the given id.
     * @param id    String representing id of User
     * @return  List of Note objects whose user's id matches the given id
     */
    List<Note> findAllByUserId(String id);
}