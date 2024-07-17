package com.revature.controller;

import com.revature.model.Part;
import com.revature.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parts")
public class PartController {

    PartService partService;

    @Autowired
    public PartController(PartService partService) {
        this.partService = partService;
    }

    // All POST Mapping

    /**
     * Endpoint for adding a new Part.
     *
     * @param part The Part to be added.
     * @return The persisted Part including it's newly assigned partId.
     */
    @PostMapping
    public ResponseEntity<Part> addPart(@RequestBody Part part) {
        Part addedPart = partService.addPart(part);
        return new ResponseEntity<>(addedPart, HttpStatus.CREATED);
    }

    // All GET Mapping

    /**
     * Endpoint for retrieving a Part given it's partId.
     *
     * @param partId The partId of a Part.
     * @return The associated Part, empty body if not found.
     */
    @GetMapping("/{partId}")
    public ResponseEntity<Part> getPart(@PathVariable int partId) {
        Part part = partService.getPart(partId);
        if (part == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(part, HttpStatus.OK);
        }
    }

    /**
     * Endpoint for retrieving all Parts.
     *
     * @return A list of all Parts.
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
     * @return A list of all Parts with the matching partName.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Part>> getPartByName(@RequestParam("partName") String partName) {
        List<Part> parts = partService.getPartsByName(partName);
        return new ResponseEntity<>(parts, HttpStatus.OK);
    }

    // We shouldn't need any of these as we can use getPart() and extract the relevant field

//    @GetMapping("{partId}/inventory")
//    public int getInventory(@PathVariable int partId){
//        return partService.getInventory(partId);
//    }
//
//    @GetMapping("{partId}/price")
//    public BigDecimal getPrice(@PathVariable int partId){
//        return partService.getPrice(partId);
//    }
//
//    @GetMapping("{partId}/MakeModel")
//    public int getMakeModel(@PathVariable int partId){
//        return partService.getMakeModel(partId);
//    }
//
//    @GetMapping("{partId}/description")
//    public String getDescription(@PathVariable int partId){
//        return partService.getDescription(partId);
//    }

    // All PUT Mapping

    /**
     * Endpoint for updating a Part given it's partId.
     *
     * @param partId The partId of a registered Part.
     * @param part   A Part containing data to be updated.
     * @return The updated Part.
     */
    @PutMapping("/{partId}")
    public ResponseEntity<Part> updatePart(@PathVariable int partId, @RequestBody Part part) {
        Part updatedPart = partService.updatePart(partId, part);
        return new ResponseEntity<>(updatedPart, HttpStatus.OK);
    }

    // We shouldn't need any of these as we can use updatePart() to update any field

//    @PutMapping(value = "/{partId}/inventory")
//    public ResponseEntity<Part> updateInventory(@PathVariable int partId, @RequestBody int amount){
//        Part p = new Part();
//        p.setPartId(partId);
//        if(p.getPartId() == partId){
//            partService.updateInventory(partId, amount);
//            return ResponseEntity.noContent().build();
//        }else{
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PutMapping(value = "/{partId}/price")
//    public ResponseEntity<Part> updatePrice(@PathVariable int partId, @RequestBody BigDecimal amount){
//        Part p = new Part();
//        p.setPartId(partId);
//        if(p.getPartId() == partId){
//            partService.updatePrice(partId, amount);
//            return ResponseEntity.noContent().build();
//        }else{
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PutMapping(value = "/{partId}/description")
//    public ResponseEntity<Part> updateDescription(@PathVariable int partId, @RequestBody String changes){
//        Part p = new Part();
//        p.setPartId(partId);
//        if(p.getPartId() == partId){
//            partService.updateDescription(partId, changes);
//            return ResponseEntity.noContent().build();
//        }else{
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }

    // All DELETE Mapping

    /**
     * Endpoint for deleting a Part given it's partId.
     *
     * @param partId The partId of Part to be deleted.
     * @return The number of rows affected.
     */
    @DeleteMapping("/{partId}")
    public ResponseEntity<Integer> deletePart(@PathVariable int partId) {
        int rows = partService.deletePart(partId);

        if (rows != 0) {
            return new ResponseEntity<>(rows, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(rows, HttpStatus.NOT_FOUND);
        }
    }
}
