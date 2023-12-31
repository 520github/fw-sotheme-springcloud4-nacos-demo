package org.sunso.sotheme.springcloud4.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.sunso.sotheme.springcloud4.common.dto.user.UserDTO;
import org.sunso.sotheme.springcloud4.user.entity.User;

public interface UserService extends IService<User> {
    User findOneByUserId(Long userId);

    User save(Long userId);

    User save(UserDTO userDTO);

    int deleteUser(Long userId);
}
