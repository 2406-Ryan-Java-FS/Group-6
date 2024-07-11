package com.group6.revature.service;

import com.group6.revature.model.Parts;
import com.group6.revature.repository.partsRepo;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class partsServiceImpl implements partsService {

    @Autowired
    partsRepo prt;

    @Override
    public List<Parts> getAllParts(){
        return prt.findAll();
    }

    @Override
    public Parts getPart(int id){
        return prt.findById(id).orElse(null);
    }

    @Override
    public Parts addPart(Parts p){
        return prt.save(p);
    }

    @Override
    public Parts updatePart(Parts changes){
        return prt.save(changes);
    }

    @Override
    public boolean deletePart(int id){
        try{
            Parts p = getPart(id);
            if(p.getPartID() !=0){
                prt.deleteById(id);
                return true;
            }
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Parts> getPart(String name){
        return prt.findByPartName(name);
    }

    // have team double check if this would work
//    @Override
//    public boolean partAvailable(int id){
//        try{
//            Parts p = getPart(id);
//            if(p.getPartID() !=0 && p.getInventory() > 0){
//                return true;
//            }
//        }catch (IllegalArgumentException e){
//            e.printStackTrace();
//        }
//        return false;
//    }
//

    @Override
    public int getInventory(int id){
        return prt.findInventory(id);
    }
}
