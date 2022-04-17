package com.windyboy.springcloud.alibaba.service;

import com.windyboy.springcloud.alibaba.domain.Order;

public interface OrderService {

    /**
     * 创建订单
     * @param order
     */
    void create(Order order);
}
