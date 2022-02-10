package me.kycho.demo.index;

import me.kycho.demo.common.BaseTest;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IndexControllerTest extends BaseTest {

    @Test
    void index() throws Exception {
        mockMvc.perform(get("/api/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.events").exists());
    }

}