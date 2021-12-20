package com.louie.learing.springboot.entity;

import com.louie.learing.springboot.enums.OrderStatus;

/**
 * @author 苍韧
 * @date 2021/12/18
 */
public class Order {
    private int id;
    private OrderStatus status;
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    @Override
    public String toString() {
        return "订单号：" + id + ", 订单状态：" + status;
    }
}
