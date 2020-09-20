package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.common.ConvertTool;
import com.thoughtworks.capability.gtb.entrancequiz.dto.EducationDTO;
import com.thoughtworks.capability.gtb.entrancequiz.entity.EducationEntity;
import com.thoughtworks.capability.gtb.entrancequiz.entity.UserEntity;
import com.thoughtworks.capability.gtb.entrancequiz.exception.ExceptionEnum;
import com.thoughtworks.capability.gtb.entrancequiz.exception.UserNotExistException;
import com.thoughtworks.capability.gtb.entrancequiz.repository.education.EducationRepository;
import com.thoughtworks.capability.gtb.entrancequiz.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public List<EducationDTO> getEducationByUserId(Long userId) {
        if (userRepository.existsById(userId)) {
            List<EducationEntity> educations = educationRepository.findAllByUserId(userId);
            return educations.stream()
                    .map(e -> ConvertTool.convert(e, EducationDTO.class))
                    .collect(Collectors.toList());
        }
        throw new UserNotExistException(ExceptionEnum.USER_NOT_EXIST);
    }

    public EducationDTO saveEducation(Long userid, EducationDTO educationDto) {
        Optional<UserEntity> user = userRepository.findById(userid);
        if (user.isPresent()) {
            EducationEntity educationEntity = ConvertTool
                    .convert(educationDto, EducationEntity.class);
            educationEntity.setUser(user.get());
            EducationEntity savedEducation = educationRepository.save(educationEntity);
            return ConvertTool.convert(savedEducation, EducationDTO.class);
        }
        throw new UserNotExistException(ExceptionEnum.USER_NOT_EXIST);
    }
}
