package com.thoughtworks.capability.gtb.entrancequiz.repository.education;

import com.thoughtworks.capability.gtb.entrancequiz.entity.EducationEntity;
import com.thoughtworks.capability.gtb.entrancequiz.entity.UserEntity;
import com.thoughtworks.capability.gtb.entrancequiz.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class EducationRepositoryTest {
    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void should_return_list_if_education_exist() {
        UserEntity user = UserEntity.builder().name("user")
                .age(25L)
                .description("user test")
                .avatar("http://...").build();
        entityManager.persistAndFlush(user);
        entityManager.persistAndFlush(EducationEntity.builder()
                .title("education 1").description("education1 test")
                .year(2010L).user(user).build());
        entityManager.persistAndFlush(EducationEntity.builder()
                .title("education 2").description("education2 test")
                .year(2013L).user(user).build());
        List<EducationEntity> educations = educationRepository.findAllByUserId(user.getId());
        assertEquals(2, educations.size());
        assertEquals("education 1", educations.get(0).getTitle());
        assertEquals("education 2", educations.get(1).getTitle());
    }

    @Test
    public void should_return_educationDTO_when_save_success() {
        UserEntity user = UserEntity.builder().name("user")
                .age(25L)
                .description("user test")
                .avatar("http://...").build();
        entityManager.persistAndFlush(user);
        EducationEntity education1 = EducationEntity.builder()
                .title("education 1").description("education1 test")
                .year(2010L).user(user).build();
        EducationEntity savedEducation = educationRepository.save(education1);
        assertEquals(1, savedEducation.getId());
        assertEquals("education 1", savedEducation.getTitle());
    }
}