package com.thoughtworks.capability.gtb.entrancequiz.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.capability.gtb.entrancequiz.dto.UserDTO;
import com.thoughtworks.capability.gtb.entrancequiz.entity.UserEntity;
import com.thoughtworks.capability.gtb.entrancequiz.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    private UserEntity userEntity;

    @BeforeEach
    public void beforeEach() {
        userEntity = UserEntity.builder()
                .name("JYZ").age(25L)
                .description("user").avatar("http://...")
                .build();
    }

    @Nested
    class IntegrationUserController {
        @Test
        public void should_return_userDTO_when_get_user_info_successful() throws Exception {
            userRepository.save(userEntity);
            mockMvc.perform(get("/users/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name",is("JYZ")))
                    .andExpect(jsonPath("$.age",is(25)))
                    .andExpect(jsonPath("$.description",is("user")));
        }

        @Test
        public void should_return_usrDTO_when_save_user_successful() throws Exception {
            ObjectMapper objectMapper = new ObjectMapper();
            UserDTO userDTO = UserDTO.builder().name("JYZ").age(25L)
                    .description("user").avatar("http://...")
                    .build();
            mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("JYZ")))
                    .andExpect(jsonPath("$.age",is(25)))
                    .andExpect(jsonPath("$.description",is("user")));
        }
    }
}
