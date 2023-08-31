package org.sunso.sotheme.springcloud4.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sunso.sotheme.springcloud4.common.dto.data.DataDTO;
import org.sunso.sotheme.springcloud4.order.entity.Order;
import org.sunso.sotheme.springcloud4.order.feign.DataFeignClient;
import org.sunso.sotheme.springcloud4.order.mapper.OrderMapper;
import org.sunso.sotheme.springcloud4.order.service.OrderService;

import java.util.Date;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private DataFeignClient dataFeignClient;

    @Override
    public Order findOneByOrderId(Long orderId) {
        dataFeignClient.save(getDataDTO("findOneByOrderId"));
        return orderMapper.findOneByOrderId(orderId);
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
