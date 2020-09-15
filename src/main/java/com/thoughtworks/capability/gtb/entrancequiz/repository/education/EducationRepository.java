package com.thoughtworks.capability.gtb.entrancequiz.repository.education;

import com.thoughtworks.capability.gtb.entrancequiz.entity.EducationEntity;

import java.util.List;

public interface EducationRepository {
    EducationEntity saveEducation(EducationEntity educationEntity);
    List<EducationEntity> getEducationsByUserId(Long id);
}
