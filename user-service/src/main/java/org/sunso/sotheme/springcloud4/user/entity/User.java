package org.sunso.sotheme.springcloud4.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.sunso.sotheme.springcloud4.common.dto.user.UserDTO;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName()
public class User implements Serializable {
    private static final long serialVersionUID = 1;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String name;
    private Long age;
    private String sex;
    private String education;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date creationTime;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date updateTime;

    public static User emptyUser() {
        User user = new User();
        user.setId(-1L);
        user.setAge(-1L);
        return user;
    }

    public static User newInstance(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setName("name:"+userId);
        user.setAge(userId/10);
        user.setSex(String.valueOf(userId%2));
        user.setCreationTime(new Date());
        user.setUpdateTime(new Date());
        return user;
    }

    public static User newInstance(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setAge(userDTO.getAge());
        user.setName(userDTO.getName());
        user.setSex(userDTO.getSex());
        user.setCreationTime(userDTO.getCreationTime());
        user.setUpdateTime(userDTO.getUpdateTime());
        return user;
    }
}
