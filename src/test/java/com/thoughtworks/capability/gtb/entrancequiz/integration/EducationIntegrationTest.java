package com.thoughtworks.capability.gtb.entrancequiz.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.capability.gtb.entrancequiz.entity.EducationEntity;
import com.thoughtworks.capability.gtb.entrancequiz.entity.UserEntity;
import com.thoughtworks.capability.gtb.entrancequiz.repository.education.EducationRepository;
import com.thoughtworks.capability.gtb.entrancequiz.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EducationIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private UserRepository userRepository;

    private UserEntity userEntity;

    private List<EducationEntity> educations = new ArrayList<>();

    private EducationEntity education1;

    private EducationEntity education2;

    @BeforeEach
    public void beforeEach() {
        userEntity = UserEntity.builder()
                .name("JYZ").age(25L)
                .description("user").avatar("http://...")
                .build();
        education1 = EducationEntity.builder()
                .title("education 1").year(2010L).description("education 1 test").user(userEntity).build();
        education2 = EducationEntity.builder()
                .title("education 2").year(2013L).description("education 2 test").user(userEntity).build();
        educations.add(education1);
        educations.add(education2);
        userEntity.setEducations(educations);
    }

    @Nested
    class IntegrationEducationController {
        @Test
        public void should_return_education_list_when_user_exist() throws Exception {
            userRepository.save(userEntity);
            educationRepository.save(education1);
            educationRepository.save(education2);

            mockMvc.perform(get("/users/{id}/educations", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].title", is("education 1")))
                    .andExpect(jsonPath("$[1].title", is("education 2")));
        }

        @Test
        public void should_return_educationDTO_when_save_successful() throws Exception {
            ObjectMapper objectMapper = new ObjectMapper();
            userRepository.save(userEntity);
            mockMvc.perform(post("/users/{id}/educations", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(education1)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.title", is("education 1")))
                    .andExpect(jsonPath("$.year", is(2010)));
        }
    }
}
