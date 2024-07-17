package com.revature.repository;

import com.revature.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    Vehicle findByMakeModelId(Integer makeModelId);

    List<Vehicle> findAllByCustomerId(Integer customerId);
}
