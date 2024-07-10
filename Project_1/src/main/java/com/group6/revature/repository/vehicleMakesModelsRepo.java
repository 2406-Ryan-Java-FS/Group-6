package com.group6.revature.repository;

import com.group6.revature.model.VehicleMakesModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface vehicleMakesModelsRepo extends JpaRepository<VehicleMakesModels, Integer> {
}
