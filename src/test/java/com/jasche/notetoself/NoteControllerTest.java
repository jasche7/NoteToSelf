package com.jasche.notetoself;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jasche.notetoself.domain.Note;
import com.jasche.notetoself.domain.User;
import com.jasche.notetoself.repository.NoteRepository;
import com.jasche.notetoself.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.time.Instant;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Mock
    private Principal principal;

    @Mock
    private OAuth2User oAuth2User;

    @Autowired
    private NoteRepository noteRepository;

    @MockBean
    private UserRepository userRepository;

    static Note testNote;

    @BeforeAll
    static void init(@Autowired NoteRepository noteRepository){
        User user = new User("1", "testUser");
        testNote = Note.builder().dateCreated(Instant.now())
                .title("Test Note")
                .text("Test Note Contents")
                .user(user)
                .build();
        noteRepository.save(testNote);
    }

    @Test
    void shouldGetAllNotes() throws Exception {
        Mockito.when(principal.getName()).thenReturn("1");
        mockMvc.perform(get("/api/notes")
                .principal(principal))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test Note")));
    }

    @Test
    void shouldGetSpecificNote() throws Exception {
        mockMvc.perform(get("/api/note/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test Note")));
    }

    @Test
    void shouldUpdateExistingNote() throws Exception {
        Note newNote = testNote;
        newNote.setText("New Test Note");
        mockMvc.perform(put("/api/note/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(newNote)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("New Test Note")));
    }
}
