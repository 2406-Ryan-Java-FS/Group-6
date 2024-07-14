package com.group6.revature.controller;

import com.group6.revature.model.Parts;
import com.group6.revature.service.partsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/parts")
public class partsController {

    partsService ps;

    @Autowired
    public partsController(partsService ps){
        this.ps = ps;
    }

//    All get mapping

    @GetMapping("/{id}")
    public Parts getPart(@PathVariable int id){
        return ps.getPart(id);
    }

    @GetMapping
    public List<Parts> getAllParts(){
        return ps.getAllParts();
    }

    @GetMapping("/search")
    public List<Parts> getPartByName(@RequestParam("part_name") String part_name){
        return ps.getPart(part_name);
    }

    @GetMapping("{id}/inventory")
    public int getInventory(@PathVariable int id){
        return ps.getInventory(id);
    }

    @GetMapping("{id}/price")
    public BigDecimal getPrice(@PathVariable int id){
        return ps.getPrice(id);
    }

    @GetMapping("{id}/MakeModel")
    public int getMakeModel(@PathVariable int id){
        return ps.getMakeModel(id);
    }

    @GetMapping("{id}/description")
    public String getDescription(@PathVariable int id){
        return ps.getDescription(id);
    }

//    All post mapping
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Parts> addPart(@RequestBody Parts p){
        p = ps.addPart(p);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parts> updatePart(@PathVariable int id, @RequestBody Parts p){
        p.setPartID(id);

        Parts p2 = ps.getPart(id);
        if(p2.getPartID() == id){
            p = ps.updatePart(p);
            return new ResponseEntity<>(p, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

//    , consumes = "application/json"
    @PutMapping(value = "/{id}/inventory", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Parts> updateInventory(@PathVariable int id, @RequestBody int amount){
        Parts p = new Parts();
        p.setPartID(id);
        if(p.getPartID() == id){
            ps.updateInventory(id, amount);
            return ResponseEntity.noContent().build();
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

//    , consumes = "application/json"
    @PutMapping(value = "/{id}/price", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Parts> updatePrice(@PathVariable int id, @RequestBody BigDecimal amount){
        Parts p = new Parts();
        p.setPartID(id);
        if(p.getPartID() == id){
            ps.updatePrice(id, amount);
            return ResponseEntity.noContent().build();
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

//    , consumes = "application/json"
    @PutMapping(value = "/{id}/description",consumes = "application/json", produces = "application/json")
    public ResponseEntity<Parts> updateDescription(@PathVariable int id, @RequestBody String changes){
        Parts p = new Parts();
        p.setPartID(id);
        if(p.getPartID() == id){
            ps.updateDescription(id, changes);
            return ResponseEntity.noContent().build();
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

//    All to the delete mapping
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePart(@PathVariable int id){
        boolean wasDeleted = ps.deletePart(id);
        return new ResponseEntity<>(wasDeleted ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
    }
}
