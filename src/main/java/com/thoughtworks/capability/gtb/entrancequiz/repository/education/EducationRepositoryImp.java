package com.thoughtworks.capability.gtb.entrancequiz.repository.education;

import com.thoughtworks.capability.gtb.entrancequiz.entity.EducationEntity;
import com.thoughtworks.capability.gtb.entrancequiz.exception.EducationException;
import com.thoughtworks.capability.gtb.entrancequiz.exception.ExceptionEnum;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EducationRepositoryImp implements EducationRepository {

    private static List<EducationEntity> educations;

    static {
        educations = new LinkedList<>();
    }

    public static List<EducationEntity> getEducations() {
        return educations;
    }

    @Override
    public EducationEntity saveEducation(EducationEntity educationEntity) {
        try {
            educations.add(educationEntity);
            return educationEntity;
        } catch (Exception ex) {
            throw new EducationException(ExceptionEnum.ADD_EDUCATION_EXCEPTION);
        }
    }

    @Override
    public List<EducationEntity> getEducationsByUserId(Long userId) {
        return educations.stream()
                .filter(e -> e.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}
