package com.revature.controller;

import com.revature.model.Vehicle;
import com.revature.service.VehicleService;
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
    public ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicleToRegister) {
        Vehicle registeredVehicle = vehicleService.addVehicle(vehicleToRegister);
        return new ResponseEntity<>(registeredVehicle, HttpStatus.OK);
    }

    /**
     * Endpoint for updating a Vehicle given it's makeModelId.
     *
     * @param makeModelId The makeModelId of a registered Vehicle.
     * @param vehicle     containing Vehicle data to be updated.
     * @return The updated Vehicle.
     */
    @PatchMapping("/{makeModelId}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Integer makeModelId, @RequestBody Vehicle vehicle) {
        Vehicle updatedVehicle = vehicleService.updateVehicle(makeModelId, vehicle);
        return new ResponseEntity<>(updatedVehicle, HttpStatus.OK);
    }

    /**
     * Endpoint for deleting a Vehicle given it's makeModelId.
     *
     * @param makeModelId The makeModelId of Vehicle to be deleted.
     * @return The number of rows affected.
     */
    @DeleteMapping("/{makeModelId}")
    public ResponseEntity<Integer> deleteVehicle(@PathVariable Integer makeModelId) {
        int rows = vehicleService.deleteVehicle(makeModelId);

        if (rows != 0) {
            return new ResponseEntity<>(rows, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rows, HttpStatus.NOT_FOUND);
        }
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

    /**
     * Endpoint for retrieving all Vehicles registered to a particular User.
     *
     * @param customerId The userId of a registered User.
     * @return A list of all Vehicles registered to the applicable User.
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<List<Vehicle>> getVehiclesByCustomerId(@PathVariable Integer customerId) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByCustomerId(customerId);
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }
}
