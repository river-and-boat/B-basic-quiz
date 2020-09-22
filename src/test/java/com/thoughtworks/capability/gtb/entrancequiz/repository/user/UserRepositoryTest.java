package com.thoughtworks.capability.gtb.entrancequiz.repository.user;

import com.thoughtworks.capability.gtb.entrancequiz.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void should_return_user_entity_when_id_exist() {
        entityManager.persistAndFlush(UserEntity.builder()
                .name("JYZ").age(25L)
                .avatar("http://...").description("user test")
                .build());
        Optional<UserEntity> user = userRepository.findById(1L);
        assertTrue(user.isPresent());
        assertEquals(user.get(), UserEntity.builder()
                .id(1L).name("JYZ").age(25L)
                .avatar("http://...").description("user test")
                .build());
    }

    @Test
    public void should_return_user_entity_when_save_success() {
        UserEntity userEntity = UserEntity.builder()
                .name("JYZ").age(25L)
                .avatar("http://...").description("user test")
                .build();
        UserEntity savedUser = userRepository.save(userEntity);
        assertEquals(1, savedUser.getId());
        assertEquals("JYZ", savedUser.getName());
    }

    @Test
    public void should_return_true_if_user_exist() {
        entityManager.persistAndFlush(UserEntity.builder()
                .name("JYZ").age(25L)
                .avatar("http://...").description("user test")
                .build());
        boolean result = userRepository.existsById(1L);
        assertEquals(true, result);
    }

    @Test
    public void should_return_false_if_user_not_exist() {
        entityManager.persistAndFlush(UserEntity.builder()
                .name("JYZ").age(25L)
                .avatar("http://...").description("user test")
                .build());
        boolean result = userRepository.existsById(2L);
        assertEquals(false, result);
    }
}