package com.jasche.notetoself.repository;

import com.jasche.notetoself.domain.Note;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {

}