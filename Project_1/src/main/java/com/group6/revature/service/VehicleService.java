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

    /**
     * Used to retrieve all Vehicles registered to a particular User.
     *
     * @param customerId The userId of a registered User.
     * @return A list of all Vehicles registered to the applicable User.
     */
    public List<Vehicle> getVehiclesByCustomerId(Integer customerId) {

        if (customerId != null && !userRepository.existsById(customerId)) {
            throw new BadRequestException("Customer does not exist.");
        }
        return vehicleRepository.findAllByCustomerId(customerId);
    }

    /**
     * Used to update a Vehicle in the repository given it's makeModelId.
     *
     * @param makeModelId The makeModelId of a registered Vehicle.
     * @param vehicle     Vehicle containing updated information.
     * @return The updated Vehicle from the repository.
     * @throws BadRequestException if there's an issue with the client's request.
     */
    public Vehicle updateVehicle(Integer makeModelId, Vehicle vehicle) {

        if (makeModelId != null && !vehicleRepository.existsById(makeModelId)) {
            throw new BadRequestException("makeModelId is invalid.");
        }

        if (vehicle.getMake().isEmpty()) {
            throw new BadRequestException("Vehicle make cannot be blank.");
        }

        if (vehicle.getModel().isEmpty()) {
            throw new BadRequestException("Vehicle model cannot be blank.");
        }

        if (vehicle.getYear() == null) {
            throw new BadRequestException("Vehicle year cannot be blank.");
        }

        if (vehicle.getCustomerId() != null && !userRepository.existsById(vehicle.getCustomerId())) {
            throw new BadRequestException("CustomerId does not exist.");
        }

        Vehicle updatedVehicle = this.getVehicleById(makeModelId);
        updatedVehicle.setMake(vehicle.getMake());
        updatedVehicle.setModel(vehicle.getModel());
        updatedVehicle.setYear(vehicle.getYear());
        updatedVehicle.setCustomerId(vehicle.getCustomerId());
        return vehicleRepository.save(updatedVehicle);
    }

    private Vehicle getVehicleById(Integer makeModelId) {
        return vehicleRepository.findByMakeModelId(makeModelId);
    }
}
