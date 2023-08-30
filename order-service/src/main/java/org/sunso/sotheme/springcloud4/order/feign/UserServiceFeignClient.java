package org.sunso.sotheme.springcloud4.order.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("userService4")
public interface UserServiceFeignClient {

}
