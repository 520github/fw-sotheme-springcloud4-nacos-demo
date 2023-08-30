package org.sunso.sotheme.springcloud4.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.sunso.sotheme.springcloud4.order.entity.Order;

public interface OrderService extends IService<Order> {
    Order findOneByOrderId(Long orderId);
}
