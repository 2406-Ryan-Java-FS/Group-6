package com.group6.revature.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "vehicle_makes_models")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "make_model_id", updatable = false, nullable = false)
    private Integer makeModelId;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer year;

    @Column(name = "owner_id")
    private Integer ownerId;

    public Vehicle() {
    }

    public Vehicle(Integer makeModelId, String make, String model, Integer year, Integer ownerId) {
        this.makeModelId = makeModelId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.ownerId = ownerId;
    }

    public Integer getMakeModelId() {
        return makeModelId;
    }

    public void setMakeModelId(Integer makeModelId) {
        this.makeModelId = makeModelId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(makeModelId, vehicle.makeModelId) &&
                Objects.equals(make, vehicle.make) &&
                Objects.equals(model, vehicle.model) &&
                Objects.equals(year, vehicle.year) &&
                Objects.equals(ownerId, vehicle.ownerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(makeModelId, make, model, year, ownerId);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "makeModelId=" + makeModelId +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", ownerId=" + ownerId +
                '}';
    }
}
