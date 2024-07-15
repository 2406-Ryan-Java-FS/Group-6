package com.revature.service;

import com.revature.model.Part;

import java.math.BigDecimal;
import java.util.List;

public interface IPartService {

    public List<Part> getAllParts();

    public Part getPart(int id);

    public Part addPart(Part p);

    public Part updatePart(Part changes);

    public boolean deletePart(int id);

    public List<Part> getPart(String name);

    public void updateInventory(int id, int amount);

    public int getInventory(int id);

    public BigDecimal getPrice(int id);

    public void updatePrice(int id, BigDecimal amount);

    public int getMakeModel(int id);

    public void updateDescription(int id, String changes);

    public String getDescription(int id);
}
