package com.zhh.order.service.Impl;
import java.math.BigDecimal;

import com.zhh.order.bean.Order;
import com.zhh.order.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public Order createOrder(Long productId, Long userId) {
        Order order = new Order();
        order.setId(1L);
        //TODO: 总金额
        order.setTotalAmount(new BigDecimal("0"));
        order.setUserId(userId);
        order.setNickname("zhanghong");
        order.setAddress("zhh");
        //TODO: 这里应该是从数据库中查询商品列表，这里暂时用假数据
        order.setProductList(null);

        return order;
    }
}
