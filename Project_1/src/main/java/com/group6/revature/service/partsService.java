package com.group6.revature.service;

import com.group6.revature.model.Parts;

import java.util.List;

public interface partsService {

    public List<Parts> getAllParts();

    public Parts getPart(int id);

    public Parts addPart(Parts p);

    public Parts updatePart(Parts changes);

    public boolean deletePart(int id);

    public List<Parts> getPart(String name);

//    public boolean partAvailable(int id);
//
    public int getInventory(int id);
}
