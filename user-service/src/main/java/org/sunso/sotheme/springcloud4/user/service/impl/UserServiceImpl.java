package org.sunso.sotheme.springcloud4.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sunso.sotheme.springcloud4.common.dto.user.UserDTO;
import org.sunso.sotheme.springcloud4.user.entity.User;
import org.sunso.sotheme.springcloud4.user.mapper.UserMapper;
import org.sunso.sotheme.springcloud4.user.service.UserService;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findOneByUserId(Long userId) {
        return userMapper.findOneByUserId(userId);
    }

    @Override
    public User save(Long userId) {
        User user = User.newInstance(userId);
        boolean result = save(user);
        if (result) {
            return user;
        }
        return null;
    }

    @Override
    public User save(UserDTO userDTO) {
        log.info("user save data[{}]", userDTO);
        User user = User.newInstance(userDTO);
        boolean result = save(user);
        if (result) {
            return user;
        }
        return null;
    }
}
