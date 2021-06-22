package com.jasche.notetoself;

import static org.assertj.core.api.Assertions.assertThat;

import com.jasche.notetoself.controller.NoteController;
import com.jasche.notetoself.controller.RedirectController;
import com.jasche.notetoself.controller.UserController;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("prod")
class SmokeTest {
    @Autowired
    private NoteController noteController;
    @Autowired
    private RedirectController redirectController;
    @Autowired
    private UserController userController;

    @Test
    void contextLoads(){
        assertThat(noteController).isNotNull();
        assertThat(redirectController).isNotNull();
        assertThat(userController).isNotNull();
    }
}
