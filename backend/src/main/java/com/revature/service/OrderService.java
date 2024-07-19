package com.revature.service;

import com.revature.exception.BadRequestException;
import com.revature.exception.NotFoundException;
import com.revature.model.Order;
import com.revature.model.Part;
import com.revature.model.User;
import com.revature.repository.OrderRepository;
import com.revature.repository.PartRepository;
import com.revature.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    OrderRepository orderRepository;
    UserRepository userRepository;
    PartRepository partRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, PartRepository partRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.partRepository = partRepository;
    }

    /**
     * Persists an Order to the repository.
     *
     * @param order The Order to be added.
     * @return The persisted Order including its newly assigned orderId.
     * @throws BadRequestException if there's an issue with the client's request.
     * @throws NotFoundException   if the Customer or Part does not exist.
     */
    public Order addOrder(Order order) {

//        if (order.getCustomerId() == null || !userRepository.existsById(order.getCustomerId())) {
//            throw new NotFoundException("Customer Id does not exist.");
//        }

//        if (order.getPartId() == null || !partRepository.existsById(order.getPartId())) {
//            throw new NotFoundException("Part Id does not exist.");
//        }

        Part orderedPart = partRepository.findByPartId(order.getPartId());

        if (order.getQuantity() == null || order.getQuantity() < 1) {
            throw new BadRequestException("Quantity is required.");
        } else if (order.getQuantity() > orderedPart.getInventory()) {
            throw new BadRequestException("Only " + orderedPart.getInventory() + " of part: " + orderedPart.getPartName() + " remaining.");
        }

        order.setOrderDate(LocalDateTime.now());
        order.setStatus("Pending");
        order.setTotal(orderedPart.getPrice().multiply(BigDecimal.valueOf(order.getQuantity())));
        return orderRepository.save(order);
    }

    /**
     * Retrieves an Order from the repository given its orderId.
     *
     * @param orderId The orderId of an Order.
     * @return The associated Order object.
     * @throws BadRequestException if the orderId is invalid.
     */
    public Order getOrder(Integer orderId) {

        if (orderId == null || !orderRepository.existsById(orderId)) {
            throw new BadRequestException("Order Id is invalid.");
        }

        return orderRepository.findByOrderId(orderId);
    }

    /**
     * Updates an Order in the repository given its orderId.
     *
     * @param orderId The orderId of a registered Order.
     * @param order   Order containing updated information.
     * @return The updated Order from the repository.
     * @throws BadRequestException if there's an issue with the client's request.
     * @throws NotFoundException   if the Customer or Part does not exist.
     */
    public Order updateOrder(Integer orderId, Order order) {

        if (orderId == null || !orderRepository.existsById(orderId)) {
            throw new BadRequestException("Order Id is invalid.");
        }

        if (order.getCustomerId() != null && !userRepository.existsById(order.getCustomerId())) {
            throw new NotFoundException("Customer Id does not exist.");
        }

        if (order.getPartId() != null && !partRepository.existsById(order.getPartId())) {
            throw new NotFoundException("Part Id does not exist.");
        }

        Order updatedOrder = this.getOrder(order.getOrderId());

        if (order.getCustomerId() != null && !updatedOrder.getCustomerId().equals(order.getCustomerId())) {
            updatedOrder.setCustomerId(order.getCustomerId());
        }

        if (order.getStatus() != null && !updatedOrder.getStatus().equals(order.getStatus())) {
            if (order.getStatus().equals("Pending") || order.getStatus().equals("Shipped")
                    || order.getStatus().equals("Delivered") || order.getStatus().equals("Cancelled")) {
                updatedOrder.setStatus(order.getStatus());
            } else {
                throw new BadRequestException("Status must be 'Pending', 'Shipped', 'Delivered', or 'Cancelled'");
            }
        }

        if (order.getPartId() != null && !updatedOrder.getPartId().equals(order.getPartId())) {
            updatedOrder.setPartId(order.getPartId());
        }

        Part orderedPart = partRepository.findByPartId(updatedOrder.getPartId());

        if (order.getQuantity() != null && !updatedOrder.getQuantity().equals(order.getQuantity())) {
            if (order.getQuantity() < 0) {
                throw new BadRequestException("Quantity must be non-negative.");
            } else {
                updatedOrder.setQuantity(order.getQuantity());
            }
        }

        if (updatedOrder.getStatus().equals("Cancelled") || updatedOrder.getQuantity() == 0) {
            updatedOrder.setStatus("Cancelled");
            updatedOrder.setQuantity(0);
            updatedOrder.setTotal(BigDecimal.valueOf(0));
        } else {
            updatedOrder.setTotal(orderedPart.getPrice().multiply(BigDecimal.valueOf(updatedOrder.getQuantity())));
        }

        return orderRepository.save(updatedOrder);
    }

    /**
     * Deletes an Order given its orderId.
     *
     * @param orderId The orderId of Order to be deleted.
     * @return true if the deletion is successful, false if the Order with the given orderId is not found.
     */
    public boolean deleteOrder(Integer orderId) {
        if (orderId != null && orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieves all Orders from the repository.
     *
     * @return A list of all Orders.
     */
    public List<Order> viewOrders() {
        return orderRepository.findAll();
    }

    public List<Order> viewUserOrders(User user) {
        Integer customerId = user.getUserId();

        return orderRepository.findAllByCustomerId(customerId);
    }
}
