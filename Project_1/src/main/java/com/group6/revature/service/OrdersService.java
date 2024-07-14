package com.group6.revature.service;
import com.group6.revature.model.Orders;
import java.util.List;

import java.util.List;

public interface OrdersService {

    public Orders addOrder(Orders o);
    public Orders getOrder(int id);
    public Orders updateOrder(Orders up);
    public Orders deleteOrder(int id);
    public List<Orders> viewOrders();

}
