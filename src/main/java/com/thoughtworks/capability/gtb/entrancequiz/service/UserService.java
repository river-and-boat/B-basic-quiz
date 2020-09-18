package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.common.ConvertTool;
import com.thoughtworks.capability.gtb.entrancequiz.dto.UserDto;
import com.thoughtworks.capability.gtb.entrancequiz.entity.UserEntity;
import com.thoughtworks.capability.gtb.entrancequiz.repository.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getUserById(Long id) {
        UserEntity userEntity = userRepository.getUserInfoById(id);
        return ConvertTool.convertUserEntityToUser(userEntity);
    }

    public UserDto saveUser(UserDto userDto) {
        UserEntity userEntity = ConvertTool.convertUserToUserEntity(userDto);
        UserEntity savedUserEntity = userRepository.saveUser(userEntity);
        return ConvertTool.convertUserEntityToUser(savedUserEntity);
    }
}
