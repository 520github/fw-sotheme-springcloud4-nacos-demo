package org.sunso.sotheme.springcloud4.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.sunso.sotheme.springcloud4.user.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User findOneByUserId(@Param("userId") Long userId);
}
