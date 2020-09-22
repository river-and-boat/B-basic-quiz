package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.dto.EducationDTO;
import com.thoughtworks.capability.gtb.entrancequiz.entity.EducationEntity;
import com.thoughtworks.capability.gtb.entrancequiz.entity.UserEntity;
import com.thoughtworks.capability.gtb.entrancequiz.exception.UserNotExistException;
import com.thoughtworks.capability.gtb.entrancequiz.repository.education.EducationRepository;
import com.thoughtworks.capability.gtb.entrancequiz.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EducationServiceTest {

    private EducationService educationService;

    private EducationEntity educationEntity;

    private EducationDTO educationDTO;

    @Mock
    private EducationRepository educationRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        educationService = new EducationService(educationRepository, userRepository);
        educationEntity = EducationEntity.builder()
                .id(1L).title("education 1").description("education test")
                .year(2000L).build();
        educationDTO = EducationDTO.builder()
                .title("education 1").description("education test")
                .year(2000L).build();
    }

    @Nested
    class GetEducationByUserId {
        @Test
        public void should_return_education_list_when_user_exist() {
            List<EducationEntity> educations = new ArrayList<>();
            educations.add(educationEntity);
            when(userRepository.existsById(1L)).thenReturn(true);
            when(educationRepository.findAllByUserId(1L)).thenReturn(educations);
            List<EducationDTO> result = educationService.getEducationByUserId(1L);
            assertEquals(1, result.size());
            assertEquals("education 1", result.get(0).getTitle());
        }

        @Test
        public void should_throw_exception_when_user_not_exist() {
            when(userRepository.existsById(1L)).thenReturn(false);
            UserNotExistException userNotExistException = assertThrows(UserNotExistException.class,
                    () -> educationService.getEducationByUserId(1L),
                    "Expected doThing() to throw, but it didn't");
            assertEquals("user is not exist",
                    userNotExistException.getExceptionEnum().getMessage());
        }
    }

    @Nested
    class SaveEducation {
        @Test
        public void should_return_educationDTO_when_save_education() {
            UserEntity user = UserEntity.builder().id(1L).age(25L)
                    .name("JYZ").avatar("http://..")
                    .description("education test").build();
            Optional<UserEntity> userEntity = Optional.of(user);
            when(userRepository.findById(1L)).thenReturn(userEntity);
            educationEntity.setDescription("saved education");
            when(educationRepository.save(any())).thenReturn(educationEntity);
            EducationDTO educationDTO = educationService
                    .saveEducation(1L, EducationServiceTest.this.educationDTO);
            assertEquals("saved education", educationDTO.getDescription());
        }

        @Test
        public void should_throw_exception_when_user_is_not_exist() {
            Optional<UserEntity> userEntity = Optional.empty();
            when(userRepository.findById(1L)).thenReturn(userEntity);
            UserNotExistException userNotExistException = assertThrows(UserNotExistException.class,
                    () -> educationService.saveEducation(1L, educationDTO),
                    "Expected doThing() to throw, but it didn't");
            assertEquals("user is not exist",
                    userNotExistException.getExceptionEnum().getMessage());
        }
    }
}