package com.group6.revature.controller;

import com.group6.revature.model.Order;
import com.group6.revature.service.OrdersServiceImplemented;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    OrdersServiceImplemented orderService;

    @Autowired
    public OrderController(OrdersServiceImplemented orderService) {
        this.orderService = orderService;
    }

    /**
     * Endpoint for creating a new Order.
     *
     * @param order The Order to be created.
     * @return The persisted Order including it's newly assigned orderId.
     */
    @PostMapping
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {
        Order addedOrder = orderService.addOrder(order);
        return new ResponseEntity<>(addedOrder, HttpStatus.OK);
    }

    /**
     * Endpoint for retrieving an Order given it's orderId.
     *
     * @param orderId The orderId of an Order.
     * @return The associated Order, empty body if not found.
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable int orderId) {
        Order order = orderService.getOrder(orderId);
        if (order == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
    }

    /**
     * Endpoint for updating an Order given it's orderId.
     *
     * @param orderId The orderId of an Order.
     * @param order   containing Order data to be updated.
     * @return The updated Order.
     */
    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable int orderId, @RequestBody Order order) {
        Order updatedOrder = orderService.updateOrder(orderId, order);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    /**
     * Endpoint for deleting an Order given it's orderId.
     *
     * @param orderId The orderId of the Order to be deleted.
     * @return The number of rows affected.
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Integer> deleteOrders(@PathVariable int orderId) {
        int rows = orderService.deleteOrder(orderId);

        if (rows != 0) {
            return new ResponseEntity<>(rows, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rows, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint for retrieving all Orders.
     *
     * @return A list of all Orders.
     */
    @GetMapping
    public ResponseEntity<List<Order>> viewOrders() {
        List<Order> orders = orderService.viewOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
