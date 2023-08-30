package org.sunso.sotheme.springcloud4.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.sunso.sotheme.springcloud4.common.dto.user.UserDTO;
import org.sunso.sotheme.springcloud4.order.entity.Order;
import org.sunso.sotheme.springcloud4.order.feign.UserFeignClient;
import org.sunso.sotheme.springcloud4.order.service.OrderService;

@RequestMapping("/order")
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserFeignClient userFeignClient;


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
}
