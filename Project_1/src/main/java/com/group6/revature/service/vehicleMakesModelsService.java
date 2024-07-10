package com.group6.revature.service;

import com.group6.revature.model.VehicleMakesModels;

public interface vehicleMakesModelsService {


    public VehicleMakesModels addVehicle(VehicleMakesModels v);
    public VehicleMakesModels getVehicle(int id);
    public VehicleMakesModels updateVehicle(VehicleMakesModels change);

//    public List<VehicleMakesModels> getAllVehicle();

}
