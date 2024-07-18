package com.revature.service;

import com.revature.exception.BadRequestException;
import com.revature.exception.ConflictException;
import com.revature.exception.NotFoundException;
import com.revature.model.Part;
import com.revature.repository.OrderRepository;
import com.revature.repository.PartRepository;
import com.revature.repository.UserRepository;
import com.revature.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PartService implements IPartService {

    PartRepository partRepository;
    UserRepository userRepository;
    VehicleRepository vehicleRepository;
    OrderRepository orderRepository;

    public PartService(PartRepository partRepository, UserRepository userRepository,
                       VehicleRepository vehicleRepository, OrderRepository orderRepository) {
        this.partRepository = partRepository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.orderRepository = orderRepository;
    }

    @Autowired

    /**
     * Persists a Part to the repository.
     *
     * @param part The Part to be added.
     * @return The persisted Part including its newly assigned partId.
     * @throws BadRequestException if there's an issue with the client's request.
     * @throws NotFoundException   if the Seller or Vehicle does not exist.
     */
    public Part addPart(Part part) {

        if (part.getSellerId() == null || !userRepository.existsById(part.getSellerId())) {
            throw new NotFoundException("Seller does not exist.");
        }

        if (part.getMakeModelId() == null || !vehicleRepository.existsById(part.getMakeModelId())) {
            throw new NotFoundException("Vehicle does not exist.");
        }

        if (part.getPartName() == null || part.getPartName().isEmpty()
                || part.getDescription() == null || part.getDescription().isEmpty()
                || part.getPrice() == null || part.getInventory() == null) {
            throw new BadRequestException("Part Name, Description, Price, and Inventory are required.");
        }

        part.setCreatedAt(LocalDateTime.now());
        part.setUpdatedAt(part.getCreatedAt());
        return partRepository.save(part);
    }

    /**
     * Retrieves a Part from the repository given its partId.
     *
     * @param partId The partId of a Part.
     * @return The associated Part object.
     * @throws BadRequestException if the partId is invalid.
     */
    public Part getPart(Integer partId) {

        if (partId == null || !partRepository.existsById(partId)) {
            throw new BadRequestException("Part Id is invalid.");
        }

        return partRepository.findByPartId(partId);
    }

    /**
     * Updates a Part in the repository given its partId.
     *
     * @param partId The partId of a registered Part.
     * @param part   Part containing updated information.
     * @return The updated Part from the repository.
     * @throws BadRequestException if there's an issue with the client's request.
     * @throws NotFoundException   if the Part, Seller or Vehicle does not exist.
     */
    public Part updatePart(Integer partId, Part part) {

        if (!partRepository.existsById(partId)) {
            throw new NotFoundException("Part does not exist");
        }

        Part updatedPart = partRepository.findByPartId(partId);

        if (part.getPartName() != null && !part.getPartName().isEmpty()) {
            updatedPart.setPartName(part.getPartName());
        }

        if (part.getDescription() != null && !part.getDescription().isEmpty()) {
            updatedPart.setDescription(part.getDescription());
        }

        if (part.getPrice() != null) {
            if (part.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                throw new BadRequestException("Price must be greater than 0");
            } else {
                updatedPart.setPrice(part.getPrice());
            }
        }

        if (part.getSellerId() != null) {
            if (!userRepository.existsById(part.getSellerId())) {
                throw new NotFoundException("Seller does not exist.");
            } else {
                updatedPart.setSellerId(part.getSellerId());
            }
        }

        if (part.getMakeModelId() != null) {
            if (!vehicleRepository.existsById(part.getMakeModelId())) {
                throw new NotFoundException("Vehicle does not exist.");
            } else {
                updatedPart.setMakeModelId(part.getMakeModelId());
            }
        }

        if (part.getInventory() != null) {
            if (part.getInventory() < 0) {
                throw new BadRequestException("Inventory can't be less than 0");
            } else {
                updatedPart.setInventory(part.getInventory());
            }
        }

        part.setUpdatedAt(LocalDateTime.now());
        return partRepository.save(updatedPart);
    }

    /**
     * Deletes a Part given its partId.
     *
     * @param partId The partId of the Part to be deleted.
     * @return true if the deletion is successful, false if the Vehicle with the given partId is not found.
     * @throws BadRequestException if there's an issue with the client's request.
     * @throws ConflictException   if the Part has already been ordered.
     */
    public boolean deletePart(Integer partId) {

        if (partId == null || !partRepository.existsById(partId)) {
            throw new BadRequestException("Part Id is invalid");
        }

        if (orderRepository.existsByPartId(partId)) {
            throw new ConflictException("Cannot delete Part as it's linked to existing Order(s)");
        }

        if (partRepository.existsById(partId)) {
            partRepository.deleteById(partId);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieves all Parts from the repository.
     *
     * @return A list of all Parts.
     */
    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    /**
     * Retrieves all Parts with the specified partName.
     *
     * @param partName The name of the Part to be found.
     * @return A list of all matching Parts.
     */
    public List<Part> getPartsByName(String partName) {
        return partRepository.findAllByPartName(partName);
    }
}
