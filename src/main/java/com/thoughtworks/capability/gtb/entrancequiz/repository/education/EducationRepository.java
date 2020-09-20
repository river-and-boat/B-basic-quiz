package com.thoughtworks.capability.gtb.entrancequiz.repository.education;

import com.thoughtworks.capability.gtb.entrancequiz.entity.EducationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EducationRepository extends CrudRepository<EducationEntity, Long> {

//    EducationEntity saveEducation(EducationEntity educationEntity);
    List<EducationEntity> findAllByUserId(Long id);
}
