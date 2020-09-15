package com.thoughtworks.capability.gtb.entrancequiz.common;

import com.thoughtworks.capability.gtb.entrancequiz.domain.Education;
import com.thoughtworks.capability.gtb.entrancequiz.domain.User;
import com.thoughtworks.capability.gtb.entrancequiz.entity.EducationEntity;
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

    public static Education convertEducationEntityToEducation(EducationEntity educationEntity) {
        return Education.builder().title(educationEntity.getTitle())
                .year(educationEntity.getYear()).userId(educationEntity.getUserId())
                .description(educationEntity.getDescription()).build();
    }

    public static EducationEntity convertEducationToEducationEntity(Education education) {
        return EducationEntity.builder().title(education.getTitle())
                .year(education.getYear()).userId(education.getUserId())
                .description(education.getDescription()).build();
    }
}
