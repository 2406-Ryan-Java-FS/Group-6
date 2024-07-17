package com.revature.service;

import com.revature.exception.BadRequestException;
import com.revature.model.Part;
import com.revature.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartService implements IPartService {

    PartRepository partRepository;

    @Autowired
    public PartService(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    /**
     * Used to persist a Part to the repository.
     *
     * @param part The Part to be added.
     * @return The persisted Part including it's newly assigned partId.
     * @throws BadRequestException if there's an issue with the client's request.
     */
    public Part addPart(Part part) {

        // TODO: Ensure fields conform to data constraints

        return partRepository.save(part);
    }

    /**
     * Used to retrieve a Part from the repository given it's partId.
     *
     * @param partId The partId of a Part.
     * @return The associated Part object, null if partId not found.
     */
    public Part getPart(int partId) {
        return partRepository.findByPartId(partId);
    }

    /**
     * Used to update a Part in the repository given it's partId.
     *
     * @param partId The partId of a registered Part.
     * @param part   Part containing updated information.
     * @return The updated Part from the repository.
     * @throws BadRequestException if there's an issue with the client's request.
     */
    public Part updatePart(Integer partId, Part part) {

        // TODO: Ensure fields conform to data constraints

        Part updatedPart = this.getPart(partId);
        updatedPart.setPartName(part.getPartName());
        updatedPart.setDescription(part.getDescription());
        updatedPart.setPrice(part.getPrice());
        updatedPart.setSellerId(part.getSellerId());
        updatedPart.setMakeModelId(part.getMakeModelId());
        updatedPart.setInventory(part.getInventory());
        updatedPart.setCreatedAt(part.getCreatedAt());
        updatedPart.setUpdatedAt(part.getUpdatedAt());
        return partRepository.save(updatedPart);
    }

    /**
     * Used to delete a Part given it's partId.
     *
     * @param partId The partId of Part to be deleted.
     * @return The number of rows affected.
     */
    public int deletePart(Integer partId) {
        if (partId != null && partRepository.existsById(partId)) {
            partRepository.deleteById(partId);
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Used to retrieve all Parts from the repository.
     *
     * @return A list of all Parts.
     */
    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    /**
     * TODO: add Javadoc comment
     */
    public List<Part> getPartsByName(String partName) {
        return partRepository.findAllByPartName(partName);
    }

    // We shouldn't need any of these as we can use getPart() and updatePart()

//    public void updateInventory(int partId, int amount){
//        partRepository.updateInventory(partId,amount);
//    }
//
//    public int getInventory(int partId){
//        return partRepository.findInventory(partId);
//    }
//
//    public BigDecimal getPrice(int partId){
//        return partRepository.getPrice(partId);
//    }
//
//    public void updatePrice(int partId, BigDecimal amount){
//        partRepository.updatePrice(partId,amount);
//    }
//
//    public int getMakeModel(int partId){
//        return partRepository.getMakeModel(partId);
//    }
//
//    public void updateDescription(int partId, String changes){
//        partRepository.updateDescription(partId,changes);
//    }
//
//    public String getDescription(int partId){
//        return partRepository.getDescription(partId);
//    }
}
