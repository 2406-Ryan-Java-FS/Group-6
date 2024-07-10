import java.util.Date;
import java.util.Objects;
package com.group6.revature.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="parts")
public class Parts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int partID;

    @Column(nullable = false)
    private String partName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double price;

    @Column
    private int sellerID;

    @Column
    private int makeModelID;

    @Column(nullable = false)
    private int inventory;

    @Column(nullable = false)
    private Date createdAt;

    @Column
    private Date updatedAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Parts() {
    }

    public Parts(int partID, String partName, String description, double price, int sellerID, int makeModelID, int inventory, Date createdAt, Date updatedAt) {
        this.partID = partID;
        this.partName = partName;
        this.description = description;
        this.price = price;
        this.sellerID = sellerID;
        this.makeModelID = makeModelID;
        this.inventory = inventory;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getPartID() {
        return partID;
    }

    public void setPartID(int partID) {
        this.partID = partID;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSellerID() {
        return sellerID;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }

    public int getMakeModelID() {
        return makeModelID;
    }

    public void setMakeModelID(int makeModelID) {
        this.makeModelID = makeModelID;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parts parts)) return false;
        return partID == parts.partID && Double.compare(price, parts.price) == 0 && sellerID == parts.sellerID && makeModelID == parts.makeModelID && inventory == parts.inventory && Objects.equals(partName, parts.partName) && Objects.equals(description, parts.description) && Objects.equals(createdAt, parts.createdAt) && Objects.equals(updatedAt, parts.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partID, partName, description, price, sellerID, makeModelID, inventory, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Parts{" +
                "partID=" + partID +
                ", partName='" + partName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", sellerID=" + sellerID +
                ", makeModelID=" + makeModelID +
                ", inventory=" + inventory +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}