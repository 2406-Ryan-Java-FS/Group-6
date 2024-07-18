package com.revature.controller;

import com.revature.exception.BadRequestException;
import com.revature.exception.ConflictException;
import com.revature.exception.NotFoundException;
import com.revature.model.Part;
import com.revature.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parts")
@CrossOrigin
public class PartController {

    PartService partService;

    @Autowired
    public PartController(PartService partService) {
        this.partService = partService;
    }

    /**
     * Endpoint for adding a new Part.
     *
     * @param part The Part to be added.
     * @return If successful, returns the persisted Part with its newly assigned partId, along with a 201 status code.
     * If unsuccessful, returns a String message indicating the failure reason, along with a 400 or 404 status code.
     */
    @PostMapping
    public ResponseEntity<Object> addPart(@RequestBody Part part) {
        try {
            Part addedPart = partService.addPart(part);
            return new ResponseEntity<>(addedPart, HttpStatus.CREATED);
        } catch (BadRequestException bre) {
            return new ResponseEntity<>(bre.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException nfe) {
            return new ResponseEntity<>(nfe.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint for retrieving a Part given its partId.
     *
     * @param partId The partId of the Part to retrieve.
     * @return If successful, returns the Part along with a 200 status code.
     * If unsuccessful, returns a String message indicating the failure reason along with a 400 status code.
     */
    @GetMapping("/{partId}")
    public ResponseEntity<Object> getPart(@PathVariable int partId) {

        try {
            Part existingPart = partService.getPart(partId);
            return new ResponseEntity<>(existingPart, HttpStatus.OK);
        } catch (BadRequestException bre) {
            return new ResponseEntity<>(bre.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint for updating a Part given its partId.
     *
     * @param partId The partId of the existing Part to be updated.
     * @param part   The Part object containing the updated data.
     * @return If successful, returns the updated Part, along with a 200 status code.
     * If unsuccessful, returns a String message indicating the failure reason, along with a 400 or 404 status code.
     */
    @PutMapping("/{partId}")
    public ResponseEntity<Object> updatePart(@PathVariable Integer partId, @RequestBody Part part) {

        try {
            Part updatedPart = partService.updatePart(partId, part);
            return new ResponseEntity<>(updatedPart, HttpStatus.OK);
        } catch (BadRequestException bre) {
            return new ResponseEntity<>(bre.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException nfe) {
            return new ResponseEntity<>(nfe.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint for deleting a Part given its partId.
     *
     * @param partId The partId of the Part to be deleted.
     * @return If successful, returns a 200 status code.
     * If unsuccessful, returns a String message indicating the failure reason, along with a 400 or 409 status code.
     */
    @DeleteMapping("/{partId}")
    public ResponseEntity<String> deletePart(@PathVariable int partId) {

        try {
            if (partService.deletePart(partId)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (BadRequestException bre) {
            return new ResponseEntity<>(bre.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ConflictException ce) {
            return new ResponseEntity<>(ce.getMessage(), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Endpoint for retrieving all Parts.
     *
     * @return A list of all Parts along with a 200 status code.
     */
    @GetMapping
    public ResponseEntity<List<Part>> getAllParts() {
        List<Part> parts = partService.getAllParts();
        return new ResponseEntity<>(parts, HttpStatus.OK);
    }

    /**
     * Endpoint for retrieving all Parts with the matching partName.
     *
     * @param partName The partName of a Part.
     * @return A list of all Parts with the matching partName along with a 200 status code.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Part>> getPartByName(@RequestParam("partName") String partName) {
        List<Part> parts = partService.getPartsByName(partName);
        return new ResponseEntity<>(parts, HttpStatus.OK);
    }
}
