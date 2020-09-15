package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.common.ConvertTool;
import com.thoughtworks.capability.gtb.entrancequiz.domain.User;
import com.thoughtworks.capability.gtb.entrancequiz.entity.UserEntity;
import com.thoughtworks.capability.gtb.entrancequiz.repository.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        UserEntity userEntity = userRepository.getUserInfoById(id);
        return ConvertTool.convertUserEntityToUser(userEntity);
    }

    public User saveUser(User user) {
        UserEntity userEntity = ConvertTool.convertUserToUserEntity(user);
        UserEntity savedUserEntity = userRepository.saveUser(userEntity);
        return ConvertTool.convertUserEntityToUser(savedUserEntity);
    }
}
