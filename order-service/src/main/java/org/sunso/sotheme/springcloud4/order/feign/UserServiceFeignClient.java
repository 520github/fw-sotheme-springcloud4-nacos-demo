package org.sunso.sotheme.springcloud4.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.sunso.sotheme.springcloud4.common.dto.user.UserDTO;

@FeignClient("userService4")
//@FeignClient(name = "userService", url = "${biz.feign.userService.url}")
public interface UserServiceFeignClient {
    @GetMapping("/user/get/{userId}")
    UserDTO findOneByUserId(@PathVariable("userId") Long userId);

    @GetMapping("/user/save/{userId}")
    UserDTO saveUser(@PathVariable("userId") Long userId);

    @PostMapping("/user/save")
    UserDTO saveUser(@RequestBody UserDTO userDTO);

    @DeleteMapping("/user/delete/{userId}")
    int deleteUser(@PathVariable("userId") Long userId);
}
