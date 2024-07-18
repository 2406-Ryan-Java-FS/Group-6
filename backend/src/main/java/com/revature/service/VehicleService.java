package com.revature.service;

import com.revature.exception.BadRequestException;
import com.revature.exception.NotFoundException;
import com.revature.model.Vehicle;
import com.revature.repository.UserRepository;
import com.revature.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, UserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    /**
     * Persists a Vehicle to the repository.
     *
     * @param vehicle The Vehicle to be added.
     * @return The persisted Vehicle including it's newly assigned makeModelId.
     * @throws BadRequestException if there's an issue with the client's request.
     */
    public Vehicle addVehicle(Vehicle vehicle) {

        if (vehicle.getMake() == null || vehicle.getMake().isEmpty()
                || vehicle.getModel() == null || vehicle.getModel().isEmpty()
                || vehicle.getYear() == null) {
            throw new BadRequestException("Vehicle make, model, and year are required.");
        }

        if (vehicle.getCustomerId() == null || !userRepository.existsById(vehicle.getCustomerId())) {
            throw new NotFoundException("Customer does not exist.");
        }

        return vehicleRepository.save(vehicle);
    }

    /**
     * Retrieves a Vehicle from the repository given it's makeModelId.
     *
     * @param makeModelId The makeModelId of a Vehicle.
     * @return The associated Vehicle object.
     * @throws BadRequestException if the makeModelId is invalid.
     */
    private Vehicle getVehicle(Integer makeModelId) {

        if (makeModelId == null || !vehicleRepository.existsById(makeModelId)) {
            throw new BadRequestException("Vehicle Id is invalid.");
        }
        return vehicleRepository.findByMakeModelId(makeModelId);
    }

    /**
     * Updates a Vehicle in the repository given it's makeModelId.
     *
     * @param makeModelId The makeModelId of a registered Vehicle.
     * @param vehicle     Vehicle containing updated information.
     * @return The updated Vehicle object.
     * @throws BadRequestException if there's an issue with the client's request.
     */
    public Vehicle updateVehicle(Integer makeModelId, Vehicle vehicle) {

        if (makeModelId == null || !vehicleRepository.existsById(makeModelId)) {
            throw new BadRequestException("Vehicle Id is invalid.");
        }

        Vehicle updatedVehicle = this.getVehicle(makeModelId);

        if (vehicle.getMake() != null && !vehicle.getMake().isEmpty()) {
            updatedVehicle.setMake(vehicle.getMake());
        }

        if (vehicle.getModel() != null && !vehicle.getModel().isEmpty()) {
            updatedVehicle.setModel(vehicle.getModel());
        }

        if (vehicle.getYear() != null) {
            updatedVehicle.setYear(vehicle.getYear());
        }

        if (vehicle.getCustomerId() != null && userRepository.existsById(vehicle.getCustomerId())) {
            updatedVehicle.setCustomerId(vehicle.getCustomerId());
        }

        return vehicleRepository.save(updatedVehicle);
    }

    /**
     * Deletes a Vehicle given its makeModelId.
     *
     * @param makeModelId The makeModelId of the Vehicle to be deleted.
     * @return true if the deletion is successful, false if the Vehicle with the given makeModelId is not found.
     */
    public boolean deleteVehicle(Integer makeModelId) {
        if (makeModelId != null && vehicleRepository.existsById(makeModelId)) {
            vehicleRepository.deleteById(makeModelId);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieves all Vehicles from the repository.
     *
     * @return A list of all Vehicles.
     */
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    /**
     * Retrieves all Vehicles registered to a specified User.
     *
     * @param customerId The userId of a registered User.
     * @return A list of all Vehicles registered to the specified User.
     * @throws BadRequestException if there's an issue with the client's request.
     */
    public List<Vehicle> getVehiclesByCustomerId(Integer customerId) {

        if (customerId == null || !userRepository.existsById(customerId)) {
            throw new BadRequestException("Customer Id is invalid.");
        }
        return vehicleRepository.findAllByCustomerId(customerId);
    }
}
