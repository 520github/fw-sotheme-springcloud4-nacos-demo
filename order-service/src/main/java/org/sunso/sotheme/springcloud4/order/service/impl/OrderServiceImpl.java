package org.sunso.sotheme.springcloud4.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sunso.sotheme.springcloud4.order.entity.Order;
import org.sunso.sotheme.springcloud4.order.mapper.OrderMapper;
import org.sunso.sotheme.springcloud4.order.service.OrderService;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Order findOneByOrderId(Long orderId) {
        return orderMapper.findOneByOrderId(orderId);
    }
}
