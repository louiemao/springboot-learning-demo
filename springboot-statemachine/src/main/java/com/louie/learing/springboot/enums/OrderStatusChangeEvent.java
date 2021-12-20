package com.louie.learing.springboot.enums;

/**
 * 订单状态改变事件
 */
public enum OrderStatusChangeEvent {
    /**
     * 支付
     */
    PAYED,
    /**
     * 发货
     */
    DELIVERY,
    /**
     * 确认收货
     */
    RECEIVED;
}
