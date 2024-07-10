package com.group6.revature.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "vehicle_makes_models")
public class VehicleMakesModels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int makeModel_id;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private int year;

    public VehicleMakesModels(int makeModel_id, String make, String model, int year) {
        this.makeModel_id = makeModel_id;
        this.make = make;
        this.model = model;
        this.year = year;
    }

    public int getMakeModel_id() {
        return makeModel_id;
    }

    public void setMakeModel_id(int makeModel_id) {
        this.makeModel_id = makeModel_id;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleMakesModels that = (VehicleMakesModels) o;
        return makeModel_id == that.makeModel_id && year == that.year && Objects.equals(make, that.make) && Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(makeModel_id, make, model, year);
    }

    @Override
    public String toString() {
        return "VehicleMakesModels{" +
                "makeModel_id=" + makeModel_id +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                '}';
    }
}
