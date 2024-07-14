package com.group6.revature.repository;

import com.group6.revature.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    List<Vehicle> findAllByCustomerId(Integer customerId);

    Vehicle findByMakeModelId(Integer makeModelId);
}
