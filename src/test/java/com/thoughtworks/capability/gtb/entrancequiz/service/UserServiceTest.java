package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.domain.User;
import com.thoughtworks.capability.gtb.entrancequiz.exception.UserException;
import com.thoughtworks.capability.gtb.entrancequiz.repository.user.UserRepositoryImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    private User user;

    @BeforeEach
    void init() {
        user = User.builder()
                .name("Jiang Yuzhou").age(25L).avatar("test avatar link")
                .description("this is a test").build();
        UserRepositoryImp.getAtomicId().set(0L);
        UserRepositoryImp.getUsers().clear();
    }

    @Test
    public void testSaveUser() {
        User user = userService.saveUser(this.user);
        assertEquals(1L, user.getId());
        assertEquals("Jiang Yuzhou", user.getName());
    }

    @Test
    public void testUserIdIncreasing() {
        User userFirst = userService.saveUser(this.user);
        User userSecond = userService.saveUser(this.user);
        assertEquals(1, userFirst.getId());
        assertEquals(2, userSecond.getId());
    }

    @Test
    public void testGetUserById() {
        User user = userService.saveUser(this.user);
        User userResult = userService.getUserById(user.getId());
        assertEquals(user.getId(), userResult.getId());
        assertEquals(user.getAge(), userResult.getAge());
        assertEquals(user.getAvatar(), userResult.getAvatar());
        assertEquals(user.getName(), userResult.getName());
        assertEquals(user.getDescription(), userResult.getDescription());
    }

    @Test
    public void testGetUserByIdWhenUserNotExist() {
        UserException userException = assertThrows(UserException.class,
                () -> userService.getUserById(1L),
                "Expected doThing() to throw, but it didn't");
        assertEquals("user is not exist",
                userException.getExceptionEnum().getErrorMessage());
    }
}