package com.jasche.notetoself;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class RedirectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRedirectToRoot() throws Exception {
        this.mockMvc.perform(get("/private"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
    }

}
