package com.group6.revature.controller;

import com.group6.revature.model.Vehicle;
import com.group6.revature.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
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
    @PostMapping
    public ResponseEntity<Vehicle> registerVehicle(@RequestBody Vehicle vehicleToRegister) {
        Vehicle registeredVehicle = vehicleService.addVehicle(vehicleToRegister);
        return new ResponseEntity<>(registeredVehicle, HttpStatus.OK);
    }

    /**
     * Endpoint for retrieving all Vehicles.
     *
     * @return A list of all Vehicles.
     */
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

}
