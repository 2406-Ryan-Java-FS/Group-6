package com.group6.revature.repository;

import com.group6.revature.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findByOrderId(Integer orderId);
}
