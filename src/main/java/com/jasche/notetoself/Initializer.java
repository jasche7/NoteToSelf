package com.jasche.notetoself;

import com.jasche.notetoself.domain.Note;
import com.jasche.notetoself.repository.NoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
class Initializer implements CommandLineRunner {

    private final NoteRepository repository;

    public Initializer(NoteRepository repository) {
        this.repository = repository;
    }

    public void run(String... strings) {
        Note note = Note.builder().dateCreated(Instant.now())
                .author("testuser")
                .title("Test Note")
                .text("Test Note Contents")
                .build();
        repository.save(note);

        repository.findAll().forEach(System.out::println);


    }
}
