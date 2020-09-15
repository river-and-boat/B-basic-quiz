package com.thoughtworks.capability.gtb.entrancequiz.repository.user;

import com.thoughtworks.capability.gtb.entrancequiz.entity.UserEntity;

public interface UserRepository {
    UserEntity getUserInfoById(Long id);
    UserEntity saveUser(UserEntity user);
    boolean existUser(Long userId);
}
