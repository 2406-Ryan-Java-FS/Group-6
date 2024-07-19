package com.revature.controller;

import com.revature.exception.BadRequestException;
import com.revature.exception.NotFoundException;
import com.revature.model.Order;
import com.revature.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin
public class OrderController {

    OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Endpoint for creating a new Order.
     *
     * @param order The Order to be created.
     * @return If successful, returns the persisted Order including its newly assigned orderId, along with a 201 status code.
     * If unsuccessful, returns a String message indicating the failure reason, along with a 400 or 404 status code.
     */
    @PostMapping
    public ResponseEntity<Object> addOrder(@RequestBody Order order) {
        try {
            Order addedOrder = orderService.addOrder(order);
            return new ResponseEntity<>(addedOrder, HttpStatus.CREATED);
        } catch (BadRequestException bre) {
            return new ResponseEntity<>(bre.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException nfe) {
            return new ResponseEntity<>(nfe.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint for retrieving an Order given its orderId.
     *
     * @param orderId The orderId of the Order to retrieve.
     * @return If successful, returns the Order along with a 200 status code.
     * If unsuccessful, returns a String message indicating the failure reason along with a 400 status code.
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<Object> getOrder(@PathVariable int orderId) {

        try {
            Order existingOrder = orderService.getOrder(orderId);
            return new ResponseEntity<>(existingOrder, HttpStatus.OK);
        } catch (BadRequestException bre) {
            return new ResponseEntity<>(bre.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint for updating an Order given its orderId.
     *
     * @param orderId The orderId of the Order to be updated.
     * @param order   The Order object containing the updated data.
     * @return If successful, returns the updated Order, along with a 200 status code.
     * If unsuccessful, returns a String message indicating the failure reason, along with a 400 or 404 status code.
     */
    @PutMapping("/{orderId}")
    public ResponseEntity<Object> updateOrder(@PathVariable int orderId, @RequestBody Order order) {
        try {
            Order updatedOrder = orderService.updateOrder(orderId, order);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (BadRequestException bre) {
            return new ResponseEntity<>(bre.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException nfe) {
            return new ResponseEntity<>(nfe.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint for deleting an Order given its orderId.
     *
     * @param orderId The orderId of the Order to be deleted.
     * @return Returns a 200 status code if the deletion is successful, and a 404 status code if the Order is not found.
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Integer> deleteOrders(@PathVariable int orderId) {

        if (orderService.deleteOrder(orderId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint for retrieving all Orders.
     *
     * @return A list of all Orders along with a 200 status code.
     */
    @GetMapping
    public ResponseEntity<List<Order>> viewOrders() {
        List<Order> orders = orderService.viewOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
