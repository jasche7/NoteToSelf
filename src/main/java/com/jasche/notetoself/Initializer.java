package com.jasche.notetoself;

import com.jasche.notetoself.domain.Author;
import com.jasche.notetoself.domain.Note;
import com.jasche.notetoself.repository.AuthorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.stream.Stream;

@Component
class Initializer implements CommandLineRunner {

    private final AuthorRepository repository;

    public Initializer(AuthorRepository repository) {
        this.repository = repository;
    }

    public void run(String... strings) {
        Stream.of("testUser1", "testUser2", "testUser3", "testUser4").forEach(name -> repository.save(new Author(name)));

        Author user1 = repository.findByName("testUser1");
        Note note = Note.builder().dateCreated(Instant.now())
                .title("Test Note")
                .text("Test Note Contents")
                .build();
        user1.setNotes(Collections.singleton(note));
        repository.save(user1);

        repository.findAll().forEach(System.out::println);


    }
}
