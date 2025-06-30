package com.zhh.order.service;

import com.zhh.order.bean.Order;

public interface OrderService {
    Order createOrder(Long productId, Long userId);
}
