package com.revature.service;

import com.revature.model.Part;
import com.revature.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PartService implements IPartService {

    @Autowired
    PartRepository prt;

    @Override
    public List<Part> getAllParts(){
        return prt.findAll();
    }

    @Override
    public Part getPart(int id){
        return prt.findById(id).orElse(null);
    }

    @Override
    public Part addPart(Part p){
        return prt.save(p);
    }

    @Override
    public Part updatePart(Part changes){
        return prt.save(changes);
    }

    @Override
    public boolean deletePart(int id){
        try{
            Part p = getPart(id);
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
    public List<Part> getPart(String part_name){
        return prt.findByPartName(part_name);
    }

    //need to keep an eye on the updates, there might be a slight problem
    //wont know till testing of the method
    @Override
    public void updateInventory(int id, int amount){
        prt.updateInventory(id,amount);
    }

    @Override
    public int getInventory(int id){
        return prt.findInventory(id);
    }

    @Override
    public BigDecimal getPrice(int id){
        return prt.getPrice(id);
    }

    @Override
    public void updatePrice(int id, BigDecimal amount){
        prt.updatePrice(id,amount);
    }

    @Override
    public int getMakeModel(int id){
        return prt.getMakeModel(id);
    }

    @Override
    public void updateDescription(int id, String changes){
        prt.updateDescription(id,changes);
    }

    @Override
    public String getDescription(int id){
        return prt.getDescription(id);
    }
}
