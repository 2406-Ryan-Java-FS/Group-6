package com.revature.service;

import com.revature.exception.BadRequestException;
import com.revature.exception.ConflictException;
import com.revature.exception.NotFoundException;
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
     * Used to persist a User to the repository.
     *
     * @param user The User to be added.
     * @return The persisted User including it's newly assigned userId.
     * @throws ConflictException   if there's already a User with the given username or password.
     * @throws BadRequestException if there's an issue with the client's request.
     */
    public User addUser(User user) {

        // TODO: Ensure fields conform to data constraints

        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new ConflictException("Username is already taken");
        }

        existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new ConflictException("Email is already registered");
        }

        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    /**
     * Used to retrieve a User from the repository given it's userId.
     *
     * @param userId The userId of a User.
     * @return The associated User, null if userId not found.
     * @throws NotFoundException if the userId doesn't match an existing User.
     */
    public User getUser(int userId) {

        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User not found.");
        }
        return userRepository.findByUserId(userId);
    }

    /**
     * Used to update a User in the repository given it's userId.
     *
     * @param userId The userId of a registered Vehicle.
     * @param user   User containing updated information.
     * @return The updated User from the repository.
     * @throws BadRequestException if there's an issue with the client's request.
     */
    public User updateUser(int userId, User user) {

        User existingUser = userRepository.findById(userId).orElse(null);

        if (existingUser == null) {
            throw new BadRequestException("User not found");
        }

        if (user.getUsername() != null && !user.getUsername().equals(existingUser.getUsername())) {
            existingUser.setUsername(user.getUsername());
        }

        if (user.getPassword() != null && !user.getPassword().equals(existingUser.getPassword())) {
            existingUser.setPassword(user.getPassword());
        }

        if (existingUser.getRole() != "Admin" && (existingUser.getRole() == "Customer" || user.getRole() == "Seller")) {
            throw new BadRequestException("You are not allowed to change your role");
        }

        return userRepository.save(user);
    }

    /**
     * Used to delete a User given valid login credentials.
     *
     * @param user containing valid login credentials for the User to be deleted.
     */
    public void deleteUser(User user) throws UnauthorizedException, IllegalArgumentException {

        User validUser = this.verifyUser(user);

        if (validUser != null) {
            userRepository.deleteById(user.getUserId());
        }
    }

    /**
     * Used to delete a User given valid admin login credentials.
     *
     * @param userId The userId of the User to be deleted.
     * @param admin  containing valid admin login credentials.
     */
    public void deleteUser(int userId, User admin) throws IllegalArgumentException {

        User validUser = this.verifyUser(admin);

        if (!validUser.getRole().equalsIgnoreCase("Admin")) {
            throw new UnauthorizedException("Only users with role 'Admin' can delete accounts.");
        }

        userRepository.deleteById(userId);
    }

    /**
     * Used to verify a User login.
     *
     * @param user User containing the username and password to verify.
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