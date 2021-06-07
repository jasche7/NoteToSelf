package com.jasche.notetoself.controller;

import com.jasche.notetoself.domain.Note;
import com.jasche.notetoself.domain.User;
import com.jasche.notetoself.repository.NoteRepository;
import com.jasche.notetoself.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
import java.security.Principal;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
class NoteController {

    private final Logger log = LoggerFactory.getLogger(NoteController.class);
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteController(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/notes")
    public Collection<Note> groups(Principal principal) {
        return noteRepository.findAllByUserId(principal.getName());
    }


    @GetMapping("/note/{id}")
    public ResponseEntity<Note> getNote(@PathVariable Long id) {
        Optional<Note> note = noteRepository.findById(id);
        return note.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/note")
    public ResponseEntity<Note> createNote(@Valid @RequestBody Note note, @AuthenticationPrincipal OAuth2User principal) throws URISyntaxException {
        log.info("Request to create note: {}", note);
        Map<String, Object> details = principal.getAttributes();
        String userId = details.get("sub").toString();

        if(note.getDateCreated() == null) {
            Instant time = Instant.now();
            log.info("dateCreated has been default set to: {}", time);
            note.setDateCreated(time);
        }

        // check to see if user already exists
        Optional<User> user = userRepository.findById(userId);
        note.setUser(user.orElse(new User(userId,
                details.get("name").toString())));

        Note result = noteRepository.save(note);
        return ResponseEntity.created(new URI("/api/note"))
                .body(result);
    }

    @PutMapping("/note/{id}")
    public ResponseEntity<Note> updateNote(@Valid @RequestBody Note note) {
        log.info("Request to update note: {}", note);
        Note result = noteRepository.save(note);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/note/{id}")
    public ResponseEntity<Long> deleteGroup(@PathVariable Long id) {
        log.info("Request to delete note: {}", id);
        noteRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}