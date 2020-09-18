package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.dto.EducationDto;
import com.thoughtworks.capability.gtb.entrancequiz.dto.UserDto;
import com.thoughtworks.capability.gtb.entrancequiz.exception.UserNotExistException;
import com.thoughtworks.capability.gtb.entrancequiz.repository.education.EducationRepositoryImp;
import com.thoughtworks.capability.gtb.entrancequiz.repository.user.UserRepositoryImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// GTB: 这里可以不用@SpringBootTest 了，但测试相应的要做一点修改，UserService 依赖的 Repository 需要传入一个 mock 实现，看看 @InjectMocks
@SpringBootTest
class EducationDtoServiceTest {

    @Autowired
    private EducationService educationService;

    @Autowired
    private UserService userService;

    private UserDto userDto;
    private EducationDto educationDtoHighSchool;
    private EducationDto educationDtoCollege;

    @BeforeEach
    void init() {
        userDto = UserDto.builder()
                .name("Jiang Yuzhou").age(25L).avatar("test avatar link")
                .description("this is a test").build();
        educationDtoHighSchool = EducationDto.builder()
                .description("test education high school").title("education high school")
                .year(1985L).build();
        educationDtoCollege = EducationDto.builder()
                .description("test education college").title("education college")
                .year(1998L).build();
        UserRepositoryImp.getAtomicId().set(0L);
        UserRepositoryImp.getUsers().clear();
        EducationRepositoryImp.getEducations().clear();
    }

    @Test
    public void testAddEducation() {
        UserDto userDto = userService.saveUser(this.userDto);
        educationDtoHighSchool.setUserId(userDto.getId());
        educationDtoCollege.setUserId(userDto.getId());

        EducationDto educationDtoHighSchoolResult = educationService.saveEducation(educationDtoHighSchool);
        EducationDto educationDtoCollegeResult = educationService.saveEducation(educationDtoCollege);

        assertEquals("education high school", educationDtoHighSchoolResult.getTitle());
        assertEquals("education college", educationDtoCollegeResult.getTitle());
    }

    @Test
    public void testAddEducationWithUserNotExist() {
        UserNotExistException userNotExistException = assertThrows(UserNotExistException.class,
                () -> educationService.saveEducation(educationDtoHighSchool),
                "Expected doThing() to throw, but it didn't");
        assertEquals("user is not exist",
                userNotExistException.getExceptionEnum().getMessage());
    }

    @Test
    public void testGetEducationsWithExistRecords() {
        UserDto userDto = userService.saveUser(this.userDto);
        educationDtoHighSchool.setUserId(userDto.getId());
        educationDtoCollege.setUserId(userDto.getId());

        EducationDto educationDtoHighSchoolResult = educationService.saveEducation(educationDtoHighSchool);
        EducationDto educationDtoCollegeResult = educationService.saveEducation(educationDtoCollege);

        List<EducationDto> educationDtos = educationService.getEducationByUserId(userDto.getId());

        assertEquals(2, educationDtos.size());
        assertEquals(educationDtoHighSchoolResult.getTitle(), educationDtos.get(0).getTitle());
        assertEquals(educationDtoCollegeResult.getTitle(), educationDtos.get(1).getTitle());
    }

    @Test
    public void testGetEducationWithNoneUserRecord() {
        Long notExistUserId = 1L;
        UserNotExistException userNotExistException = assertThrows(UserNotExistException.class,
                () -> educationService.getEducationByUserId(notExistUserId),
                "Expected doThing() to throw, but it didn't");
        assertEquals("user is not exist",
                userNotExistException.getExceptionEnum().getMessage());
    }
}