package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.common.ConvertTool;
import com.thoughtworks.capability.gtb.entrancequiz.dto.UserDTO;
import com.thoughtworks.capability.gtb.entrancequiz.entity.UserEntity;
import com.thoughtworks.capability.gtb.entrancequiz.repository.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id).get();
        return ConvertTool.convert(userEntity, UserDTO.class);
    }

    public UserDTO saveUser(UserDTO userDto) {
        UserEntity userEntity = ConvertTool.convert(userDto, UserEntity.class);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return ConvertTool.convert(savedUserEntity, UserDTO.class);
    }
}
