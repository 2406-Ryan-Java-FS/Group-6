package com.revature.controller;

import com.revature.exception.BadRequestException;
import com.revature.exception.NotFoundException;
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
     * @param vehicle The Vehicle to be registered.
     * @return If successful, returns the persisted Vehicle with its newly assigned makeModelId, along with a 201 status code.
     * If unsuccessful, returns a String message indicating the failure reason, along with a 400 or 404 status code.
     */
    @PostMapping
    public ResponseEntity<Object> addVehicle(@RequestBody Vehicle vehicle) {
        try {
            Vehicle registeredVehicle = vehicleService.addVehicle(vehicle);
            return new ResponseEntity<>(registeredVehicle, HttpStatus.CREATED);
        } catch (BadRequestException bre) {
            return new ResponseEntity<>(bre.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException nfe) {
            return new ResponseEntity<>(nfe.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint for updating a Vehicle given its makeModelId.
     *
     * @param makeModelId The makeModelId of the registered Vehicle to be updated.
     * @param vehicle     The Vehicle object containing the updated data.
     * @return If successful, returns the updated Vehicle, along with a 200 status code.
     * If unsuccessful, returns a String message indicating the failure reason, along with a 400 status code.
     */
    @PatchMapping("/{makeModelId}")
    public ResponseEntity<Object> updateVehicle(@PathVariable Integer makeModelId, @RequestBody Vehicle vehicle) {
        try {
            Vehicle updatedVehicle = vehicleService.updateVehicle(makeModelId, vehicle);
            return new ResponseEntity<>(updatedVehicle, HttpStatus.OK);
        } catch (BadRequestException bre) {
            return new ResponseEntity<>(bre.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint for deleting a Vehicle given its makeModelId.
     *
     * @param makeModelId The makeModelId of the Vehicle to be deleted.
     * @return Returns a 200 status code if the deletion is successful, and a 404 status code if the Vehicle is not found.
     */
    @DeleteMapping("/{makeModelId}")
    public ResponseEntity<HttpStatus> deleteVehicle(@PathVariable Integer makeModelId) {

        if (vehicleService.deleteVehicle(makeModelId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint for retrieving all Vehicles.
     *
     * @return A list of all Vehicles along with a 200 status code.
     */
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    /**
     * Endpoint for retrieving all Vehicles registered to a specified User.
     *
     * @param customerId The userId of a registered User.
     * @return If successful, returns a list of all Vehicles registered to the specified User.
     * If unsuccessful, returns a String message indicating the failure reason, along with a 400 status code.
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<Object> getVehiclesByCustomerId(@PathVariable Integer customerId) {
        try {
            List<Vehicle> vehicles = vehicleService.getVehiclesByCustomerId(customerId);
            return new ResponseEntity<>(vehicles, HttpStatus.OK);
        } catch (BadRequestException bre) {
            return new ResponseEntity<>(bre.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
