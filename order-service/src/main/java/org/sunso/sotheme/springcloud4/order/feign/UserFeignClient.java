package org.sunso.sotheme.springcloud4.order.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.sunso.sotheme.springcloud4.common.dto.user.UserDTO;

@FeignClient(name = "user", url = "${biz.feign.userService.url}")
public interface UserFeignClient {

    @GetMapping("/user/get/{userId}")
    UserDTO findOneByUserId(@PathVariable("userId") Long userId);
}
