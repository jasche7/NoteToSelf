package com.jasche.notetoself;

import com.jasche.notetoself.controller.NoteController;
import com.jasche.notetoself.domain.Note;
import com.jasche.notetoself.domain.User;
import com.jasche.notetoself.repository.NoteRepository;
import com.jasche.notetoself.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import java.security.Principal;
import java.time.Instant;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private Principal principal;

    @Autowired
    private NoteRepository noteRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    void shouldGetAllNotes() throws Exception {
        mockMvc.perform(get("/api/notes").principal(principal)).andExpect(status().isOk());
    }

    @Test
    void shouldGetSpecificNote() throws Exception {
        Note note = Note.builder().dateCreated(Instant.now())
                .title("Test Note")
                .text("Test Note Contents")
                .build();
        noteRepository.save(note);
        mockMvc.perform(get("/api/note/1")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Test Note")));
    }

}
