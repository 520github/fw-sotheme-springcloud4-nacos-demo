package org.sunso.sotheme.springcloud4.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sunso.sotheme.springcloud4.common.dto.data.DataDTO;
import org.sunso.sotheme.springcloud4.common.dto.user.UserDTO;
import org.sunso.sotheme.springcloud4.user.entity.User;
import org.sunso.sotheme.springcloud4.user.feign.DataFeignClient;
import org.sunso.sotheme.springcloud4.user.mapper.UserMapper;
import org.sunso.sotheme.springcloud4.user.service.UserService;

import java.util.Date;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DataFeignClient dataFeignClient;

    @Override
    public User findOneByUserId(Long userId) {
        dataFeignClient.save(getDataDTO("findOneByUserId"));
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

    @Override
    public int deleteUser(Long userId) {
        User user = findOneByUserId(userId);
        if (user == null) {
            return -1;
        }
        boolean result = removeById(userId);
        if (result) {
            return 1;
        }
        return 0;
    }

    private DataDTO getDataDTO(String eventId) {
        DataDTO dto = new DataDTO();
        dto.setServerId("userService");
        dto.setServerName("userService4");
        dto.setEventId(eventId);
        dto.setEventName(eventId);
        dto.setEventTime(new Date());
        return dto;
    }
}
