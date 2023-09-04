package org.sunso.sotheme.springcloud4.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.sunso.sotheme.springcloud4.common.dto.data.DataDTO;
import org.sunso.sotheme.springcloud4.common.dto.user.UserDTO;
import org.sunso.sotheme.springcloud4.order.entity.Order;
import org.sunso.sotheme.springcloud4.order.feign.DataFeignClient;
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

    @Autowired
    private DataFeignClient dataFeignClient;


    @GetMapping("test/timeout/{time}")
    public int testTimeout(@PathVariable int time) throws InterruptedException {
        if (time <10) {
            Thread.sleep(2000);
        }
        return time;
    }

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
        dataFeignClient.save(getDataDTO("/user/get/"+userId));
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

    @GetMapping("/user/service/delete/{userId}")
    public int deleteUser(@PathVariable Long userId) {
        return userServiceFeignClient.deleteUser(userId);
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

    private DataDTO getDataDTO(String eventId) {
        DataDTO dto = new DataDTO();
        dto.setServerId("orderService");
        dto.setServerName("orderService4");
        dto.setEventId(eventId);
        dto.setEventName(eventId);
        dto.setEventTime(new Date());
        return dto;
    }
}
