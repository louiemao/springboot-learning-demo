package com.louie.learing.springboot;

import com.louie.learing.springboot.entity.Order;
import com.louie.learing.springboot.service.OrderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Test {

    public static void main(String[] args) {
        Thread.currentThread().setName("主线程");
        ConfigurableApplicationContext context = SpringApplication.run(Test.class, args);

        OrderService orderService = (OrderService)context.getBean("orderService");
        Order order1 = orderService.create();
        Order order2 = orderService.create();
        orderService.pay(order1.getId());
        new Thread("客户线程") {
            @Override
            public void run() {
                orderService.deliver(order1.getId());
                orderService.receive(order1.getId());
            }
        }.start();
        orderService.pay(order2.getId());
        orderService.deliver(order2.getId());
        orderService.receive(order2.getId());
        System.out.println("全部订单状态：" + orderService.getOrders());
    }
}