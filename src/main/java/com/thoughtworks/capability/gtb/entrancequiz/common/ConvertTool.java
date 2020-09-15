package com.thoughtworks.capability.gtb.entrancequiz.common;

import com.thoughtworks.capability.gtb.entrancequiz.domain.User;
import com.thoughtworks.capability.gtb.entrancequiz.entity.UserEntity;

public class ConvertTool {
    public static UserEntity convertUserToUserEntity(User user) {
        return UserEntity.builder()
                .id(user.getId()).age(user.getAge())
                .avatar(user.getAvatar()).description(user.getDescription())
                .name(user.getName()).build();
    }

    public static User convertUserEntityToUser(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId()).age(userEntity.getAge())
                .avatar(userEntity.getAvatar()).description(userEntity.getDescription())
                .name(userEntity.getName()).build();
    }
}
