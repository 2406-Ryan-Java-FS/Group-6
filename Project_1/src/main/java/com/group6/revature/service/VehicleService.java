package com.group6.revature.service;

import com.group6.revature.exception.BadRequestException;
import com.group6.revature.model.Vehicle;
import com.group6.revature.repository.VehicleRepository;
import com.group6.revature.repository.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final userRepo userRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, userRepo userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    /**
     * Used to persist a Vehicle to the repository.
     *
     * @param vehicle The Vehicle to be added.
     * @return The persisted Vehicle including it's newly assigned game_id.
     * @throws BadRequestException if there's an issue with the client's request.
     */
    public Vehicle addVehicle(Vehicle vehicle) {

        if (vehicle.getCustomerId() != null && !userRepository.existsById(vehicle.getCustomerId())) {
            throw new BadRequestException("Customer does not exist.");
        }
        return vehicleRepository.save(vehicle);
    }

    /**
     * Used to retrieve all Vehicles from the repository.
     *
     * @return A list of all Vehicles.
     */
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }
}
