package com.revature.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name="parts")
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int part_id;

    @Column(name = "part_name", nullable = false)
    private String part_name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column
    private int seller_id;

    @Column
    private int make_model_id;

    @Column(nullable = false)
    private int inventory;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime created_at;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime updated_at;

    public LocalDateTime getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public Part() {
    }

    public Part(int part_id, String part_name, String description, BigDecimal price, int seller_id, int make_model_id, int inventory, LocalDateTime created_at, LocalDateTime updated_at) {
        this.part_id = part_id;
        this.part_name = part_name;
        this.description = description;
        this.price = price;
        this.seller_id = seller_id;
        this.make_model_id = make_model_id;
        this.inventory = inventory;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getPartID() {
        return part_id;
    }

    public void setPartID(int part_id) {
        this.part_id = part_id;
    }

    public String getPartName() {
        return part_name;
    }

    public void setPartName(String part_name) {
        this.part_name = part_name;
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

    public int getSellerID() {
        return seller_id;
    }

    public void setSellerID(int seller_id) {
        this.seller_id = seller_id;
    }

    public int getMakeModelID() {
        return make_model_id;
    }

    public void setMakeModelID(int make_model_id) {
        this.make_model_id = make_model_id;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Parts parts)) return false;
//        return part_id == parts.part_id && Double.compare(price, parts.price) == 0 && seller_id == parts.seller_id && make_model_id == parts.make_model_id && inventory == parts.inventory && Objects.equals(part_name, parts.part_name) && Objects.equals(description, parts.description) && Objects.equals(created_at, parts.created_at) && Objects.equals(updated_at, parts.updated_at);
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(part_id, part_name, description, price, seller_id, make_model_id, inventory, created_at, updated_at);
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Part parts = (Part) o;
        return part_id == parts.part_id && seller_id == parts.seller_id && make_model_id == parts.make_model_id && inventory == parts.inventory && Objects.equals(part_name, parts.part_name) && Objects.equals(description, parts.description) && Objects.equals(price, parts.price) && Objects.equals(created_at, parts.created_at) && Objects.equals(updated_at, parts.updated_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(part_id, part_name, description, price, seller_id, make_model_id, inventory, created_at, updated_at);
    }

    @Override
    public String toString() {
        return "Parts{" +
                "part_id=" + part_id +
                ", part_name='" + part_name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", seller_id=" + seller_id +
                ", make_model_id=" + make_model_id +
                ", inventory=" + inventory +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}