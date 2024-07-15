package com.group6.revature.controller;

import com.group6.revature.model.Orders;
import com.group6.revature.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrdersController {

    OrdersService os;

    @Autowired
    public OrdersController(OrdersService os) {

        this.os = os;
    }

    @GetMapping
    public List<Orders> viewOrders() {
        return os.viewOrders();
    }

    @GetMapping("/{id}")
    public Orders getOrder(@PathVariable int id) {
        return os.getOrder(id);
    }

    @PostMapping//(consumes = "/application/json", produces = "/application/json")
    public ResponseEntity<Orders> addOrder(@RequestBody Orders o) {
        o = os.addOrder(o);
        return new ResponseEntity<>(o, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Orders> updateOrder(@PathVariable int id, @RequestBody Orders o) {
        o.setOrderId(id);

        Orders o2 = os.getOrder(id);
        if(o2.getOrderId() == id) {
            o = os.updateOrder(o);
            return new ResponseEntity<>(o, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Orders> deleteOrders(@PathVariable int id) {
        Orders o = os.deleteOrder(id);
        if (o != null) {
            return new ResponseEntity<>(o, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }



}
