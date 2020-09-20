package com.thoughtworks.capability.gtb.entrancequiz.repository.user;

import com.thoughtworks.capability.gtb.entrancequiz.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
//    UserEntity getUserInfoById(Long id);
//    UserEntity saveUser(UserEntity user);
//    boolean existUser(Long userId);
}
