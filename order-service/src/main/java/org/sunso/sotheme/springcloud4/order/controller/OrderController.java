package org.sunso.sotheme.springcloud4.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.sunso.sotheme.springcloud4.common.dto.user.UserDTO;
import org.sunso.sotheme.springcloud4.order.entity.Order;
import org.sunso.sotheme.springcloud4.order.feign.UserFeignClient;
import org.sunso.sotheme.springcloud4.order.feign.UserServiceFeignClient;
import org.sunso.sotheme.springcloud4.order.service.OrderService;

import java.util.Date;

@RequestMapping("/order")
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private UserServiceFeignClient userServiceFeignClient;


    @GetMapping("/get/{orderId}")
    public Order findOneByOrderId(@PathVariable Long orderId) {
        Order order = orderService.findOneByOrderId(orderId);
        if (order == null) {
            order = Order.emptyOrder();
        }
        return order;
    }

    @GetMapping("/user/get/{userId}")
    public UserDTO findOneUserByUserId(@PathVariable Long userId) {
        return userFeignClient.findOneByUserId(userId);
    }

    @GetMapping("/user/service/get/{userId}")
    public UserDTO findOneUserServiceByUserId(@PathVariable Long userId) {
        return userServiceFeignClient.findOneByUserId(userId);
    }

    @GetMapping("/user/service/save/{userId}")
    public UserDTO saveUser(@PathVariable Long userId) {
        return userServiceFeignClient.saveUser(getUserDTO(userId));
    }

    private UserDTO getUserDTO(Long userId) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setName("name:"+userId);
        userDTO.setAge(userId/10);
        userDTO.setSex(String.valueOf(userId%2));
        userDTO.setCreationTime(new Date());
        userDTO.setUpdateTime(new Date());
        return userDTO;
    }
}
