package com.revature.service;

import com.revature.exception.BadRequestException;
import com.revature.model.Order;
import com.revature.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IOrderService {

    OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Used to persist an Order to the repository.
     *
     * @param order The Order to be added.
     * @return The persisted Order including it's newly assigned orderId.
     * @throws BadRequestException if there's an issue with the client's request.
     */
    public Order addOrder(Order order) {

        // TODO: Ensure fields conform to data constraints

        return orderRepository.save(order);
    }

    /**
     * Used to retrieve an Order from the repository given it's orderId.
     *
     * @param orderId The orderId of an Order.
     * @return The associated Order, null if orderId not found.
     */
    public Order getOrder(Integer orderId) {

        if (!orderRepository.existsById(orderId)) {
            return null;
        }
        return orderRepository.findByOrderId(orderId);
    }

    /**
     * Used to update an Order in the repository given it's orderId.
     *
     * @param orderId The orderId of a registered Order.
     * @param order   Order containing updated information.
     * @return The updated Order from the repository.
     * @throws BadRequestException if there's an issue with the client's request.
     */
    public Order updateOrder(Integer orderId, Order order) {

        // TODO: Ensure fields conform to data constraints

        Order updatedOrder = this.getOrder(order.getOrderId());
        updatedOrder.setCustomerId(order.getCustomerId());
        updatedOrder.setOrderDate(order.getOrderDate());
        updatedOrder.setStatus(order.getStatus());
        updatedOrder.setTotal(order.getTotal());
        updatedOrder.setPartId(order.getPartId());
        updatedOrder.setQuantity(order.getQuantity());
        return orderRepository.save(updatedOrder);
    }

    /**
     * Used to delete an Order given it's orderId.
     *
     * @param orderId The orderId of Order to be deleted.
     * @return The number of rows affected.
     */
    public Integer deleteOrder(Integer orderId) {
        if (orderId != null && orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Used to retrieve all Orders from the repository.
     *
     * @return A list of all Orders.
     */
    public List<Order> viewOrders() {
        return orderRepository.findAll();
    }
}
