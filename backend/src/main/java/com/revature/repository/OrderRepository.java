package com.revature.repository;

import com.revature.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findByOrderId(Integer orderId);

    boolean existsByPartId(Integer partId);
}
