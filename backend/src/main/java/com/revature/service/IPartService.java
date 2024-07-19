package com.revature.service;

import com.revature.model.Part;

import java.util.List;

public interface IPartService {

    Part addPart(Part part);

    Part getPart(Integer partId);

    Part updatePart(Integer partId, Part part);

    boolean deletePart(Integer partId);

    List<Part> getAllParts();

    List<Part> getPartsByName(String partName);
}
