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

/**
 * Rest controller for http requests related to Notes.
 */
@RestController
@RequestMapping("/api")
public class NoteController {

    private final Logger log = LoggerFactory.getLogger(NoteController.class);
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    /**
     * Constructor for NoteController, initializing with repositories.
     * @param noteRepository    repository for Note objects
     * @param userRepository    repository for User objects
     */
    public NoteController(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    /**
     * This mapping to /api/notes for GET requests returns a Collection of Note objects
     * which share the same user id number as the Principal given as an argument.
     * @param principal current user
     * @return  Collection of Note objects
     */
    @GetMapping("/notes")
    public Collection<Note> groups(Principal principal) {
        return noteRepository.findAllByUserId(principal.getName());
    }


    /**
     * This mapping to /api/note/{id} for GET requests returns a single Note whose
     * id matches the id given as an argument. If no note is found, HTTP 404 Not Found is
     * returned instead.
     * @param id    id number of note to be retrieved
     * @return  HTTP response with body containing a Note if found, or else an HTTP 404 Not Found
     */
    @GetMapping("/note/{id}")
    public ResponseEntity<Note> getNote(@PathVariable Long id) {
        Optional<Note> note = noteRepository.findById(id);
        return note.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * This mapping to /api/note for POST requests creates a Note object which includes
     * the user details of the principal and with dateCreated set to current time.
     * Sets the Note's User to current User if User is in UserRepository, otherwise creates
     * a new User.
     * @param note  request body including details for the Note to be created
     * @param principal current user
     * @return  http response whose body includes the note that was created
     * @throws URISyntaxException thrown when URI() is given invalid string
     */
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

    /**
     * This mapping to /api/note/{id} for PUT requests updates an existing Note in
     * the repository.
     * @param note  http request whose body contains a Note
     * @return  http response whose body contains the updated Note
     */
    @PutMapping("/note/{id}")
    public ResponseEntity<Note> updateNote(@Valid @RequestBody Note note) {
        log.info("Request to update note: {}", note);
        Note result = noteRepository.save(note);
        return ResponseEntity.ok().body(result);
    }

    /**
     * This mapping to /api/note/{id} for DELETE requests deletes a Note from the
     * repository according to the id given as an argument.
     * @param id    id of Note to be deleted
     * @return  http response with status OK
     */
    @DeleteMapping("/note/{id}")
    public ResponseEntity<Long> deleteGroup(@PathVariable Long id) {
        log.info("Request to delete note: {}", id);
        noteRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}