package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.domain.Education;
import com.thoughtworks.capability.gtb.entrancequiz.domain.User;
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

@SpringBootTest
class EducationServiceTest {

    @Autowired
    private EducationService educationService;

    @Autowired
    private UserService userService;

    private User user;
    private Education educationHighSchool;
    private Education educationCollege;

    @BeforeEach
    void init() {
        user = User.builder()
                .name("Jiang Yuzhou").age(25L).avatar("test avatar link")
                .description("this is a test").build();
        educationHighSchool = Education.builder()
                .description("test education high school").title("education high school")
                .year(1985L).build();
        educationCollege = Education.builder()
                .description("test education college").title("education college")
                .year(1998L).build();
        UserRepositoryImp.getAtomicId().set(0L);
        UserRepositoryImp.getUsers().clear();
        EducationRepositoryImp.getEducations().clear();
    }

    @Test
    public void testAddEducation() {
        User user = userService.saveUser(this.user);
        educationHighSchool.setUserId(user.getId());
        educationCollege.setUserId(user.getId());

        Education educationHighSchoolResult = educationService.saveEducation(educationHighSchool);
        Education educationCollegeResult = educationService.saveEducation(educationCollege);

        assertEquals("education high school", educationHighSchoolResult.getTitle());
        assertEquals("education college", educationCollegeResult.getTitle());
    }

    @Test
    public void testAddEducationWithUserNotExist() {
        UserNotExistException userNotExistException = assertThrows(UserNotExistException.class,
                () -> educationService.saveEducation(educationHighSchool),
                "Expected doThing() to throw, but it didn't");
        assertEquals("user is not exist",
                userNotExistException.getExceptionEnum().getMessage());
    }

    @Test
    public void testGetEducationsWithExistRecords() {
        User user = userService.saveUser(this.user);
        educationHighSchool.setUserId(user.getId());
        educationCollege.setUserId(user.getId());

        Education educationHighSchoolResult = educationService.saveEducation(educationHighSchool);
        Education educationCollegeResult = educationService.saveEducation(educationCollege);

        List<Education> educations = educationService.getEducationByUserId(user.getId());

        assertEquals(2, educations.size());
        assertEquals(educationHighSchoolResult.getTitle(), educations.get(0).getTitle());
        assertEquals(educationCollegeResult.getTitle(), educations.get(1).getTitle());
    }

    @Test
    public void testGetEducationWithNoneRecord() {
        Long notExistUserId = 1L;
        List<Education> educations = educationService.getEducationByUserId(notExistUserId);
        assertEquals(0, educations.size());
    }
}