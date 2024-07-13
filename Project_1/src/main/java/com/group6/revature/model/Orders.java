package com.group6.revature.model;

import java.util.Date;
import java.util.Objects;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int orderID;

    @Column(nullable = false)
    private int customerID;

    @Column(nullable = false)
    private Date orderDate;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private double total;

    @Column(nullable = false)
    private int partID;

    @Column(nullable = false)
    private int quantity;

    public Orders() {
    }

    public Orders(int orderID, int customerID, Date orderDate, String status, double total, int partID, int quantity) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.orderDate = orderDate;
        this.status = status;
        this.total = total;
        this.partID = partID;
        this.quantity = quantity;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getPartID() {
        return partID;
    }

    public void setPartID(int partID) {
        this.partID = partID;
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
        return orderID == orders.orderID && customerID == orders.customerID && Double.compare(total, orders.total) == 0 && partID == orders.partID && quantity == orders.quantity && Objects.equals(orderDate, orders.orderDate) && Objects.equals(status, orders.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID, customerID, orderDate, status, total, partID, quantity);
    }

    @Override
    public String toString() {
        return "Orders{" +
                "order_id=" + orderID +
                ", customer_id=" + customerID +
                ", order_date=" + orderDate +
                ", status='" + status + '\'' +
                ", total=" + total +
                ", part_id=" + partID +
                ", quantity=" + quantity +
                '}';
    }
}

