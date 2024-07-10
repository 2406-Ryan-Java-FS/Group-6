package com.group6.revature.service;

import com.group6.revature.model.VehicleMakesModels;
import org.springframework.beans.factory.annotation.Autowired;

import com.group6.revature.repository.vehicleMakesModelsRepo;

public class vehicleMakesModelsServiceImpl implements vehicleMakesModelsService {

    @Autowired
    vehicleMakesModelsRepo vr;

    @Override
    public VehicleMakesModels addVehicle(VehicleMakesModels v) {
        return vr.save(v);
    }

    @Override
    public VehicleMakesModels getVehicle(int id) {
        return vr.getById(id);
    }

    @Override
    public VehicleMakesModels updateVehicle(VehicleMakesModels change) {
        return vr.save(change);
    }

}
