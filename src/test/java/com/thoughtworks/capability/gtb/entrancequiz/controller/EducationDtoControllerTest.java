package com.thoughtworks.capability.gtb.entrancequiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.capability.gtb.entrancequiz.entity.EducationEntity;
import com.thoughtworks.capability.gtb.entrancequiz.entity.UserEntity;
import com.thoughtworks.capability.gtb.entrancequiz.repository.education.EducationRepository;
import com.thoughtworks.capability.gtb.entrancequiz.repository.education.EducationRepositoryImp;
import com.thoughtworks.capability.gtb.entrancequiz.repository.user.UserRepository;
import com.thoughtworks.capability.gtb.entrancequiz.repository.user.UserRepositoryImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EducationDtoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE_URL = "http://localhost:8080/users/";

    private static final String URL_SUFFIX = "/educations";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private UserEntity userEntity;

    private EducationEntity educationEntity;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EducationRepository educationRepository;

    @BeforeEach
    void init() {
        userEntity = UserEntity.builder()
                .name("Jiang Yuzhou").avatar("test avatar")
                .age(25L).description("test description").build();
        educationEntity = EducationEntity.builder()
                .title("test education title").year(2020L)
                .description("education test").build();
        UserRepositoryImp.getUsers().clear();
        UserRepositoryImp.getAtomicId().set(0L);
        EducationRepositoryImp.getEducations().clear();
    }

    // GTB: 一般测试命名用全小写下划线分割，我是说一般情况下
    @Test
    public void testGetEducationByUserIdWhenIdIsNotNumber() throws Exception {
        String userId = "errorId";
        mockMvc.perform(get(BASE_URL + userId + URL_SUFFIX))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("CLIENT ERROR")))
                .andExpect(jsonPath("$.message", is("id类型不匹配")));
    }

    @Test
    public void testGetEducationByUserIdWhenUserIdNotExist() throws Exception {
        Long userId = 1L;
        mockMvc.perform(get(BASE_URL + userId + URL_SUFFIX))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("NOT FOUND")))
                .andExpect(jsonPath("$.message", is("user is not exist")));
    }

    @Test
    public void testGetEducationByUserIdInNormalCase() throws Exception {
        UserEntity userEntity = userRepository.saveUser(this.userEntity);
        educationEntity.setUserId(userEntity.getId());
        educationRepository.saveEducation(educationEntity);

        mockMvc.perform(get(BASE_URL + userEntity.getId() + URL_SUFFIX))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userId", is(1)))
                .andExpect(jsonPath("$[0].year", is(2020)))
                .andExpect(jsonPath("$[0].title", is("test education title")))
                .andExpect(jsonPath("$[0].description", is("education test")));
    }

    @Test
    public void testSaveEducationWhenUserIdNotNumber() throws Exception {
        String userId = "errorId";
        mockMvc.perform(post(BASE_URL + userId + URL_SUFFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(educationEntity)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("CLIENT ERROR")))
                .andExpect(jsonPath("$.message", is("id类型不匹配")));
    }

    @Test
    public void testSaveEducationWhenUserNotExist() throws Exception {
        Long userId = 2L;
        mockMvc.perform(post(BASE_URL + userId + URL_SUFFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(educationEntity)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("NOT FOUND")))
                .andExpect(jsonPath("$.message", is("user is not exist")));
    }

    @Test
    public void testSaveEducationWhenYearIsLessThan1900() throws Exception {
        UserEntity userEntity = userRepository.saveUser(this.userEntity);
        educationEntity.setUserId(userEntity.getId());
        educationEntity.setYear(1899L);
        mockMvc.perform(post(BASE_URL + userEntity.getId() + URL_SUFFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(educationEntity)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("CLIENT ERROR")))
                .andExpect(jsonPath("$.message", is("the parameters are not match the rule")));
    }

    @Test
    public void testSaveEducationWhenYearIsLargerThan2020() throws Exception {
        UserEntity userEntity = userRepository.saveUser(this.userEntity);
        educationEntity.setUserId(userEntity.getId());
        educationEntity.setYear(2021L);
        mockMvc.perform(post(BASE_URL + userEntity.getId() + URL_SUFFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(educationEntity)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("CLIENT ERROR")))
                .andExpect(jsonPath("$.message", is("the parameters are not match the rule")));
    }

    @Test
    public void testSaveEducationWhenTitleIsNull() throws Exception {
        UserEntity userEntity = userRepository.saveUser(this.userEntity);
        educationEntity.setUserId(userEntity.getId());
        educationEntity.setTitle(null);
        mockMvc.perform(post(BASE_URL + userEntity.getId() + URL_SUFFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(educationEntity)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("CLIENT ERROR")))
                .andExpect(jsonPath("$.message", is("the parameters are not match the rule")));
    }

    @Test
    public void testSaveEducationWhenDescriptionIsNull() throws Exception {
        UserEntity userEntity = userRepository.saveUser(this.userEntity);
        educationEntity.setUserId(userEntity.getId());
        educationEntity.setDescription(null);
        mockMvc.perform(post(BASE_URL + userEntity.getId() + URL_SUFFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(educationEntity)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("CLIENT ERROR")))
                .andExpect(jsonPath("$.message", is("the parameters are not match the rule")));
    }

    @Test
    public void testSaveEducationInNormalCase() throws Exception {
        UserEntity userEntity = userRepository.saveUser(this.userEntity);
        educationEntity.setUserId(userEntity.getId());
        mockMvc.perform(post(BASE_URL + userEntity.getId() + URL_SUFFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(educationEntity)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.year", is(2020)))
                .andExpect(jsonPath("$.title", is("test education title")))
                .andExpect(jsonPath("$.description", is("education test")));
    }
}