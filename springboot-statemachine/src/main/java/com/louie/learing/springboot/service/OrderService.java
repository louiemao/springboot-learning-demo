package com.louie.learing.springboot.service;

import java.util.Map;

import com.louie.learing.springboot.entity.Order;

public interface OrderService {
    /**
     * 创建订单
     *
     * @return
     */
    Order create();

    /**
     * 发起支付
     *
     * @param id
     * @return
     */
    Order pay(int id);

    /**
     * 订单发货
     *
     * @param id
     * @return
     */
    Order deliver(int id);

    /**
     * 订单收货
     *
     * @param id
     * @return
     */
    Order receive(int id);

    /**
     * 获取所有订单信息
     *
     * @return
     */
    Map<Integer, Order> getOrders();
}