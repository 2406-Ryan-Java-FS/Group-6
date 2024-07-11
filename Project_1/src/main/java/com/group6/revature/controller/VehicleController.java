package com.group6.revature.controller;

import com.group6.revature.model.Vehicle;
import com.group6.revature.service.VehicleService;
import com.group6.revature.service.userServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VehicleController {

    VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * Endpoint for registering a new Vehicle.
     *
     * @param vehicleToRegister The Vehicle to be registered.
     * @return The persisted Vehicle Object including it's newly assigned makeModel_id.
     */
    @PostMapping("/vehicles")
    public ResponseEntity<Vehicle> registerVehicle(@RequestBody Vehicle vehicleToRegister) {
        Vehicle registeredVehicle = vehicleService.addVehicle(vehicleToRegister);
        return new ResponseEntity<>(registeredVehicle, HttpStatus.OK);
    }

}
