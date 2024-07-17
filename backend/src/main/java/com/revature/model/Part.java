package com.revature.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "parts")
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "part_id", updatable = false)
    private Integer partId;

    @Column(name = "part_name", nullable = false)
    private String partName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "seller_id")
    private Integer sellerId;

    @Column(name = "make_model_id")
    private Integer makeModelId;

    @Column(nullable = false)
    private Integer inventory;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    public Part() {
    }

    public Part(Integer partId, String partName, String description, BigDecimal price, Integer sellerId, Integer makeModelId, Integer inventory, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.partId = partId;
        this.partName = partName;
        this.description = description;
        this.price = price;
        this.sellerId = sellerId;
        this.makeModelId = makeModelId;
        this.inventory = inventory;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getPartId() {
        return partId;
    }

    public void setPartId(Integer partId) {
        this.partId = partId;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getMakeModelId() {
        return makeModelId;
    }

    public void setMakeModelId(Integer makeModelId) {
        this.makeModelId = makeModelId;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Part part = (Part) o;
        return Objects.equals(partId, part.partId) && Objects.equals(partName, part.partName) && Objects.equals(description, part.description) && Objects.equals(price, part.price) && Objects.equals(sellerId, part.sellerId) && Objects.equals(makeModelId, part.makeModelId) && Objects.equals(inventory, part.inventory) && Objects.equals(createdAt, part.createdAt) && Objects.equals(updatedAt, part.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partId, partName, description, price, sellerId, makeModelId, inventory, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Part{" +
                "part_id=" + partId +
                ", part_name='" + partName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", seller_id=" + sellerId +
                ", make_model_id=" + makeModelId +
                ", inventory=" + inventory +
                ", created_at=" + createdAt +
                ", updated_at=" + updatedAt +
                '}';
    }
}