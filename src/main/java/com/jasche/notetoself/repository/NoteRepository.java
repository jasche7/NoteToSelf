package com.jasche.notetoself.repository;

import com.jasche.notetoself.domain.Note;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    Note findByTitle(String title);
    List<Note> findAllByUserId(String id);
}