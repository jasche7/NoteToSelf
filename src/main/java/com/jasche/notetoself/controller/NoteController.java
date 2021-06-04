package com.jasche.notetoself.controller;

import com.jasche.notetoself.domain.Note;
import com.jasche.notetoself.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Collection;

@RestController
@RequestMapping("/api")
class NoteController {

    private final Logger log = LoggerFactory.getLogger(NoteController.class);
    private NoteRepository noteRepository;

    public NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @GetMapping("/notes")
    public Collection<Note> notes() {
        return noteRepository.findAll();
    }

    @PostMapping("/notes")
    public ResponseEntity<Note> createNote(@Valid @RequestBody Note note) throws URISyntaxException {
        log.info("Request to create note: {}", note);
        if(note.getDateCreated() == null) {
            Instant time = Instant.now();
            log.info("dateCreated has been default set to: {}", time);
            note.setDateCreated(time);
        }
        Note result = noteRepository.save(note);
        return ResponseEntity.created(new URI("/api/notes"))
                .body(result);
    }

    @PutMapping("/notes/{id}")
    public ResponseEntity<Note> updateNote(@Valid @RequestBody Note note) {
        log.info("Request to update note: {}", note);
        Note result = noteRepository.save(note);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<Long> deleteGroup(@PathVariable Long id) {
        log.info("Request to delete note: {}", id);
        noteRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}