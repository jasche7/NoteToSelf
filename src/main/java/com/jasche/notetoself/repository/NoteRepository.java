package com.jasche.notetoself.repository;

import com.jasche.notetoself.domain.Note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    Note findByTitle(String title);
    List<Note> findAllByUserId(String id);
}