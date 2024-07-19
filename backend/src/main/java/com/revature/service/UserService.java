package com.revature.service;

import com.revature.dto.UserUpdateDTO;
import com.revature.exception.BadRequestException;
import com.revature.exception.ConflictException;
import com.revature.exception.UnauthorizedException;
import com.revature.model.User;
import com.revature.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService implements IUserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Persists a User to the repository.
     *
     * @param user The User to be added.
     * @return The persisted User including its newly assigned userId and createdAt.
     * @throws ConflictException   if there's already a User with the given username or password.
     * @throws BadRequestException if there's an issue with the client's request.
     */
    public User addUser(User user) {

        if (user.getUsername() == null || user.getUsername().isEmpty()
                || user.getPassword() == null || user.getPassword().isEmpty()
                || user.getEmail() == null || user.getEmail().isEmpty()
                || user.getRole() == null || user.getRole().isEmpty()) {
            throw new BadRequestException("Username, password, email, and role are required.");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ConflictException("Username is already taken");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ConflictException("Email is already registered");
        }

        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    /**
     * Retrieves a User from the repository given its userId.
     *
     * @param userId The userId of a User.
     * @return The associated User object.
     * @throws BadRequestException if the userId is invalid.
     */
    public User getUser(Integer userId) {

        if (userId == null || !userRepository.existsById(userId)) {
            throw new BadRequestException("User Id is invalid.");
        }
        return userRepository.findByUserId(userId);
    }

    /**
     * Updates a User in the repository given its userId.
     *
     * @param userId The userId of the User to be updated.
     * @param user   User object containing updated information.
     * @return The updated User object.
     * @throws BadRequestException   if there's an issue with the client's request.
     * @throws UnauthorizedException if trying to change roles without sufficient privileges.
     * @throws ConflictException     if the updated username or email are already taken.
     */
    public User updateUser(String username, UserUpdateDTO userUpdateDTO) {

        User updatedUser = userRepository.findByUsername(username);

        if (updatedUser == null) {
            throw new BadRequestException("User not found.");
        }

        if (userUpdateDTO.getUsernameNew() != null && !userUpdateDTO.getUsernameNew().isEmpty()
                && !updatedUser.getUsername().equals(userUpdateDTO.getUsernameNew())) {
            if (userRepository.existsByUsername(userUpdateDTO.getUsernameNew())) {
                throw new ConflictException("Username is already taken");
            }
            updatedUser.setUsername(userUpdateDTO.getUsernameNew());
        }

        if (userUpdateDTO.getPasswordNew() != null && !userUpdateDTO.getPasswordNew().isEmpty()
                && !updatedUser.getPassword().equals(userUpdateDTO.getPasswordNew())) {
            updatedUser.setPassword(userUpdateDTO.getPasswordNew());
        }

        // Ensure the email and role fields remain unchanged
        if (userUpdateDTO.getUsernameNew() == null && userUpdateDTO.getPasswordNew() == null) {
            throw new BadRequestException("No fields to update.");
        }

        return userRepository.save(updatedUser);
    }

    /**
     * Deletes a User given valid login credentials.
     *
     * @param user User object containing valid login credentials for the User to be deleted.
     */
    public void deleteUser(User user) throws UnauthorizedException, IllegalArgumentException {

        this.verifyUser(user);
        userRepository.deleteById(user.getUserId());
    }

    /**
     * Deletes a User given valid admin login credentials.
     *
     * @param userId The userId of the User to be deleted.
     * @param admin  User object containing valid admin login credentials.
     * @throws UnauthorizedException if a non-Admin attempts to delete a User.
     */
    public void deleteUser(int userId, User admin) throws IllegalArgumentException {

        User validUser = this.verifyUser(admin);

        if (!validUser.getRole().equalsIgnoreCase("Admin")) {
            throw new UnauthorizedException("Only users with the role 'Admin' can delete accounts.");
        }

        userRepository.deleteById(userId);
    }

    public void deleteUser(int userId) throws IllegalArgumentException {
        User existingUser = userRepository.findByUserId(userId);
        if (existingUser != null) {
            userRepository.deleteById(userId);
        } else if (existingUser == null) {
            throw new IllegalArgumentException("User already deleted or does not exist.");
        }
    }

    /**
     * Verifies a User login.
     *
     * @param user User object containing the username and password to verify.
     * @return The verified User.
     * @throws UnauthorizedException if the username and/or password are invalid.
     */
    public User verifyUser(User user) {

        User existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            return existingUser;
        } else {
            throw new UnauthorizedException("Invalid login credentials");
        }
    }
}