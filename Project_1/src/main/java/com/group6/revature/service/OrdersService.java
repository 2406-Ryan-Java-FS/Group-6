package com.group6.revature.service;

import com.group6.revature.model.Order;

import java.util.List;

public interface OrdersService {

    Order addOrder(Order order);

    Order getOrder(Integer orderId);

    Order updateOrder(Integer orderId, Order order);

    Integer deleteOrder(Integer orderId);

    List<Order> viewOrders();

}
