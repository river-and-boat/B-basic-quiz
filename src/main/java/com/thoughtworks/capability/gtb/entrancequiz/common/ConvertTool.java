package com.thoughtworks.capability.gtb.entrancequiz.common;

import com.thoughtworks.capability.gtb.entrancequiz.dto.EducationDto;
import com.thoughtworks.capability.gtb.entrancequiz.dto.UserDto;
import com.thoughtworks.capability.gtb.entrancequiz.entity.EducationEntity;
import com.thoughtworks.capability.gtb.entrancequiz.entity.UserEntity;

// GTB: + 有单独的负责转换的工具类
public class ConvertTool {
    public static UserEntity convertUserToUserEntity(UserDto userDto) {
        return UserEntity.builder()
                .id(userDto.getId()).age(userDto.getAge())
                .avatar(userDto.getAvatar()).description(userDto.getDescription())
                .name(userDto.getName()).build();
    }

    public static UserDto convertUserEntityToUser(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId()).age(userEntity.getAge())
                .avatar(userEntity.getAvatar()).description(userEntity.getDescription())
                .name(userEntity.getName()).build();
    }

    public static EducationDto convertEducationEntityToEducation(EducationEntity educationEntity) {
        return EducationDto.builder().title(educationEntity.getTitle())
                .year(educationEntity.getYear()).userId(educationEntity.getUserId())
                .description(educationEntity.getDescription()).build();
    }

    public static EducationEntity convertEducationToEducationEntity(EducationDto educationDto) {
        return EducationEntity.builder().title(educationDto.getTitle())
                .year(educationDto.getYear()).userId(educationDto.getUserId())
                .description(educationDto.getDescription()).build();
    }
}
