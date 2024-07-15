package com.group6.revature.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", updatable = false)
    private int orderId;

    @Column(name = "customer_id", nullable = false)
    private int customerId;

    @Column(name = "order_date", nullable = false)
    private Date orderDate;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(name = "part_id", nullable = false)
    private int partId;

    @Column(nullable = false)
    private int quantity;

    public Orders() {
    }

    public Orders(int orderId, int customerId, Date orderDate, String status, BigDecimal total, int partId, int quantity) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.status = status;
        this.total = total;
        this.partId = partId;
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return orderId == orders.orderId && customerId == orders.customerId && partId == orders.partId && quantity == orders.quantity && Objects.equals(orderDate, orders.orderDate) && Objects.equals(status, orders.status) && Objects.equals(total, orders.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, customerId, orderDate, status, total, partId, quantity);
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", orderDate=" + orderDate +
                ", status='" + status + '\'' +
                ", total=" + total +
                ", partId=" + partId +
                ", quantity=" + quantity +
                '}';
    }
}

