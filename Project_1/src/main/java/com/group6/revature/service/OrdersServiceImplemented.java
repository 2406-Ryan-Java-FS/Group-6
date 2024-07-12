package com.group6.revature.service;
import com.group6.revature.model.Orders;
import com.group6.revature.repository.OrdersRepo;
import com.group6.revature.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImplemented implements OrdersService {

    @Autowired
    OrdersRepo or;

    @Override
    public Orders addOrder(Orders o) {
        or.save(o);
        return o;
    }

    public Orders getOrder(int id) {
        return or.findById(id).orElse(null);
    }

    public Orders updateOrder(Orders up) {
        return or.save(up);
    }

    public Orders deleteOrder(int id) {
        try{
            Orders o = getOrder(id);
            if(o != null) {
                or.deleteById(id);
                return(o);
            }
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Orders> viewOrders() {
        return or.findAll();
    }

}
