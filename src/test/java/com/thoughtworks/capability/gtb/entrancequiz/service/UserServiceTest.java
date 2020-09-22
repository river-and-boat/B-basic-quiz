package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.dto.UserDTO;
import com.thoughtworks.capability.gtb.entrancequiz.entity.UserEntity;
import com.thoughtworks.capability.gtb.entrancequiz.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    private UserEntity userEntity;

    private UserDTO userDTO;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        userService = new UserService(userRepository);
        userEntity = UserEntity.builder()
                .id(1L).name("JYZ").age(25L)
                .avatar("http://...").description("user test")
                .build();
        userDTO = UserDTO.builder()
                .age(25L).avatar("http://...")
                .id(1L).name("JYZ")
                .description("user test").build();
    }

    @Nested
    class GetUserById {
        @Test
        public void should_return_user_when_get_user_by_id() {
            Optional<UserEntity> user = Optional.of(userEntity);
            when(userRepository.findById(1L)).thenReturn(user);
            UserDTO userDTO = userService.getUserById(1L);
            assertEquals("JYZ", userDTO.getName());
            assertEquals("http://...", userDTO.getAvatar());
            assertEquals("user test", userDTO.getDescription());
        }
    }

    @Nested
    class SaveUser {
        @Test
        public void should_return_user_when_save_user() {
            userEntity.setDescription("saved user");
            when(userRepository.save(any())).thenReturn(userEntity);
            UserDTO userDTO = userService.saveUser(UserServiceTest.this.userDTO);
            assertEquals("saved user", userDTO.getDescription());
        }
    }
}