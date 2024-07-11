package com.group6.revature.controller;

import com.group6.revature.model.Parts;
import com.group6.revature.service.partsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public Parts getPart(int id){
        return ps.getPart(id);
    }

    @GetMapping
    public List<Parts> getAllParts(){
        return ps.getAllParts();
    }

    @GetMapping("/search")
    public List<Parts> getPartByName(String name){
        return ps.getPart(name);
    }

    @GetMapping("{id}/inventory")
    public int getInventory(int id){
        return ps.getInventory(id);
    }

    @GetMapping("{id}/price")
    public double getPrice(int id){
        return ps.getPrice(id);
    }

    @GetMapping("{id}/MakeModel")
    public int getMakeModel(int id){
        return ps.getMakeModel(id);
    }

    @GetMapping("{id}/description")
    public String getDescription(int id){
        return ps.getDescription(id);
    }

//    All post mapping
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Parts> addPart(@RequestBody Parts p){
        p = ps.addPart(p);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

//    All put mapping. noticed there might be a slight problem with the update services for parts
    //wont know till i test them. If there is, I (jason) will have it fixed by the morning(7/12)
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

    @PutMapping(value = "/{id}/inventory", consumes = "application/json" , produces = "application/json")
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

    @PutMapping(value = "/{id}/price", consumes = "application/json" , produces = "application/json")
    public ResponseEntity<Parts> updatePrice(@PathVariable int id, @RequestBody double amount){
        Parts p = new Parts();
        p.setPartID(id);
        if(p.getPartID() == id){
            ps.updatePrice(id, amount);
            return ResponseEntity.noContent().build();
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{id}/inventory", consumes = "application/json" , produces = "application/json")
    public ResponseEntity<Parts> updateDescription(@PathVariable int id, @RequestBody String text){
        Parts p = new Parts();
        p.setPartID(id);
        if(p.getPartID() == id){
            ps.updateDescription(id, text);
            return ResponseEntity.noContent().build();
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

//    All to the delete mapping
    @DeleteMapping("/id")
    public ResponseEntity<Boolean> deletePart(@PathVariable int id){
        boolean wasDeleted = ps.deletePart(id);
        return new ResponseEntity<>(wasDeleted ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
    }
}
