package com.revature.service;

import com.revature.model.Part;

import java.util.List;

public interface IPartService {

    Part addPart(Part part);

    Part getPart(int partId);

    Part updatePart(Integer partId, Part part);

    int deletePart(Integer partId);

    List<Part> getAllParts();

    List<Part> getPartsByName(String partName);

    // We shouldn't need any of these as we can use getPart() and updatePart()

//    public void updateInventory(int partId, int amount);
//
//    public int getInventory(int partId);
//
//    public BigDecimal getPrice(int partId);
//
//    public void updatePrice(int partId, BigDecimal amount);
//
//    public int getMakeModel(int partId);
//
//    public void updateDescription(int partId, String changes);
//
//    public String getDescription(int partId);
}
