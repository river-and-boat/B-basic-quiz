package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.common.ConvertTool;
import com.thoughtworks.capability.gtb.entrancequiz.domain.Education;
import com.thoughtworks.capability.gtb.entrancequiz.entity.EducationEntity;
import com.thoughtworks.capability.gtb.entrancequiz.exception.ExceptionEnum;
import com.thoughtworks.capability.gtb.entrancequiz.exception.UserNotExistException;
import com.thoughtworks.capability.gtb.entrancequiz.repository.education.EducationRepository;
import com.thoughtworks.capability.gtb.entrancequiz.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EducationService {

    private final EducationRepository educationRepository;

    private final UserRepository userRepository;

    public EducationService(EducationRepository educationRepository,
                            UserRepository userRepository) {
        this.educationRepository = educationRepository;
        this.userRepository = userRepository;
    }

    public List<Education> getEducationByUserId(Long userId) {
        List<EducationEntity> educations = educationRepository.getEducationsByUserId(userId);
        return educations.stream()
                .map(ConvertTool::convertEducationEntityToEducation)
                .collect(Collectors.toList());
    }

    public Education saveEducation(Education education) {
        if (userRepository.existUser(education.getUserId())) {
            EducationEntity educationEntity = ConvertTool
                    .convertEducationToEducationEntity(education);
            EducationEntity savedEducation = educationRepository.saveEducation(educationEntity);
            return ConvertTool.convertEducationEntityToEducation(savedEducation);
        }
        throw new UserNotExistException(ExceptionEnum.USER_NOT_EXIST);
    }
}
