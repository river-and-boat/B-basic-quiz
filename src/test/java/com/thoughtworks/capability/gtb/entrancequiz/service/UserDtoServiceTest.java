package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.dto.UserDto;
import com.thoughtworks.capability.gtb.entrancequiz.exception.UserNotExistException;
import com.thoughtworks.capability.gtb.entrancequiz.repository.user.UserRepositoryImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserDtoServiceTest {

    @Autowired
    private UserService userService;

    private UserDto userDto;

    @BeforeEach
    void init() {
        userDto = UserDto.builder()
                .name("Jiang Yuzhou").age(25L).avatar("test avatar link")
                .description("this is a test").build();
        UserRepositoryImp.getAtomicId().set(0L);
        UserRepositoryImp.getUsers().clear();
    }

    @Test
    public void testSaveUser() {
        UserDto userDto = userService.saveUser(this.userDto);
        assertEquals(1L, userDto.getId());
        assertEquals("Jiang Yuzhou", userDto.getName());
    }

    @Test
    public void testUserIdIncreasing() {
        UserDto userDtoFirst = userService.saveUser(this.userDto);
        UserDto userDtoSecond = userService.saveUser(this.userDto);
        assertEquals(1, userDtoFirst.getId());
        assertEquals(2, userDtoSecond.getId());
    }

    @Test
    public void testGetUserById() {
        UserDto userDto = userService.saveUser(this.userDto);
        UserDto userDtoResult = userService.getUserById(userDto.getId());
        assertEquals(userDto.getId(), userDtoResult.getId());
        assertEquals(userDto.getAge(), userDtoResult.getAge());
        assertEquals(userDto.getAvatar(), userDtoResult.getAvatar());
        assertEquals(userDto.getName(), userDtoResult.getName());
        assertEquals(userDto.getDescription(), userDtoResult.getDescription());
    }

    @Test
    public void testGetUserByIdWhenUserNotExist() {
        UserNotExistException userNotExistException = assertThrows(UserNotExistException.class,
                () -> userService.getUserById(1L),
                "Expected doThing() to throw, but it didn't");
        assertEquals("user is not exist",
                userNotExistException.getExceptionEnum().getMessage());
    }
}