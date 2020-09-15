package com.thoughtworks.capability.gtb.entrancequiz.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.capability.gtb.entrancequiz.entity.UserEntity;
import com.thoughtworks.capability.gtb.entrancequiz.repository.user.UserRepository;
import com.thoughtworks.capability.gtb.entrancequiz.repository.user.UserRepositoryImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private static final String BASE_URL = "http://localhost:8080";

    private static final String GET_USER_PATH = "/users/";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private UserEntity userEntity;

    @BeforeEach
    void init() {
        userEntity = UserEntity.builder()
                .name("Jiang Yuzhou").avatar("test avatar")
                .age(25L).description("test description").build();
        UserRepositoryImp.getUsers().clear();
        UserRepositoryImp.getAtomicId().set(0L);
    }

    @Test
    public void testGetUserByIdWhenIdIsNotNumber() throws Exception {
        String userId = "errorId";
        mockMvc.perform(get(BASE_URL + GET_USER_PATH + userId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("CLIENT ERROR")))
                .andExpect(jsonPath("$.message", is("id类型不匹配")));
    }

    @Test
    public void testGetUserByIdInNormalCase() throws Exception {
        UserEntity userEntity = userRepository.saveUser(this.userEntity);
        mockMvc.perform(get(BASE_URL + GET_USER_PATH + userEntity.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Jiang Yuzhou")))
                .andExpect(jsonPath("$.age", is(25)))
                .andExpect(jsonPath("$.avatar", is("test avatar")))
                .andExpect(jsonPath("$.description",is("test description")));
    }

    @Test
    public void testCreateUserInfoWhenNameIsEmpty() throws Exception {
        userEntity.setName("");
        mockMvc.perform(post(BASE_URL + GET_USER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(userEntity)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("CLIENT ERROR")))
                .andExpect(jsonPath("$.message", is("the parameters are not match the rule")));
    }

    @Test
    public void testCreateUserInfoWhenAgeIsLessThan17() throws Exception {
        userEntity.setAge(16L);
        mockMvc.perform(post(BASE_URL + GET_USER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(userEntity)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("CLIENT ERROR")))
                .andExpect(jsonPath("$.message", is("the parameters are not match the rule")));
    }

    @Test
    public void testCreateUserInfoWhenAvatarIsLessThan8() throws Exception {
        userEntity.setAvatar("less");
        mockMvc.perform(post(BASE_URL + GET_USER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(userEntity)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("CLIENT ERROR")))
                .andExpect(jsonPath("$.message", is("the parameters are not match the rule")));
    }

    @Test
    public void testCreateUserInNormalCase() throws Exception {
        mockMvc.perform(post(BASE_URL + GET_USER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(userEntity)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Jiang Yuzhou")))
                .andExpect(jsonPath("$.age", is(25)))
                .andExpect(jsonPath("$.avatar", is("test avatar")))
                .andExpect(jsonPath("$.description", is("test description")));
    }

    @Test
    public void testCreateUserInfoWhenDescriptionIsNull() throws Exception {
        userEntity.setDescription(null);
        mockMvc.perform(post(BASE_URL + GET_USER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(userEntity)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Jiang Yuzhou")))
                .andExpect(jsonPath("$.age", is(25)))
                .andExpect(jsonPath("$.avatar", is("test avatar")))
                .andExpect(jsonPath("$.description", is(nullValue())));
    }
}