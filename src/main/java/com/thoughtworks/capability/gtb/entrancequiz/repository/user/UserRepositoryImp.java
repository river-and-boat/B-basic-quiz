package com.thoughtworks.capability.gtb.entrancequiz.repository.user;

import com.thoughtworks.capability.gtb.entrancequiz.entity.UserEntity;
import com.thoughtworks.capability.gtb.entrancequiz.exception.ExceptionEnum;
import com.thoughtworks.capability.gtb.entrancequiz.exception.UserException;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepositoryImp implements UserRepository {

    private static List<UserEntity> users;
    private static AtomicLong atomicId;

    private static final Long ID_INITIAL_VALUE = 0L;
    private static final Long ID_INCREASING_VALUE = 1L;

    static {
        users = new LinkedList<>();
        atomicId = new AtomicLong(ID_INITIAL_VALUE);
    }

    @Override
    public UserEntity getUserInfoById(Long id) {
        Optional<UserEntity> userEntity = users.stream()
                .filter(u -> u.getId().equals(id)).findFirst();
        if (userEntity.isPresent()) {
            return userEntity.get();
        }
        throw new UserException(ExceptionEnum.USER_NOT_EXIST);
    }

    @Override
    public UserEntity saveUser(UserEntity user) {
        user.setId(atomicId.addAndGet(ID_INCREASING_VALUE));
        try {
            users.add(user);
            return user;
        } catch (Exception ex) {
            throw new UserException(ExceptionEnum.ADD_USER_EXCEPTION);
        }
    }
}
