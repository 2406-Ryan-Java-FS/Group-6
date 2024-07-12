package com.group6.revature.service;

import com.group6.revature.model.Parts;

import java.math.BigDecimal;
import java.util.List;

public interface partsService {

    public List<Parts> getAllParts();

    public Parts getPart(int id);

    public Parts addPart(Parts p);

    public Parts updatePart(Parts changes);

    public boolean deletePart(int id);

//    public List<Parts> getPart(String name);

    public void updateInventory(int id, int amount);

    public int getInventory(int id);

    public BigDecimal getPrice(int id);

    public void updatePrice(int id, BigDecimal amount);

    public int getMakeModel(int id);

    public void updateDescription(int id, String changes);

    public String getDescription(int id);
}
