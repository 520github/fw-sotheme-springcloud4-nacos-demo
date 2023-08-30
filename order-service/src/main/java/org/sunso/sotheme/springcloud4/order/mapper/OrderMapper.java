package org.sunso.sotheme.springcloud4.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.sunso.sotheme.springcloud4.order.entity.Order;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    Order findOneByOrderId(@Param("orderId") Long orderId);
}
