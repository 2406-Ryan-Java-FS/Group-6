package com.revature.service;

import com.revature.model.Order;

import java.util.List;

public interface IOrderService {

    Order addOrder(Order order);

    Order getOrder(Integer orderId);

    Order updateOrder(Integer orderId, Order order);

    Integer deleteOrder(Integer orderId);

    List<Order> viewOrders();

}
