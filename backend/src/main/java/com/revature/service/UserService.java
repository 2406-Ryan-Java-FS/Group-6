package com.revature.service;

import com.revature.model.User;
import com.revature.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository ur;


    @Override
    public User addUser(User user) {
        User existingUser = ur.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new IllegalArgumentException("Username is already taken");
        }
        existingUser = ur.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new IllegalArgumentException("Email is already registered");
        }

        user.setCreated_at(LocalDateTime.now());
        return ur.save(user);
    }

    @Override
    public User getUser(int id) {
        return ur.findById(id).orElse(null);
    }

    @Override
    public User loginValidate(User user) {
        String username = user.getUsername();
        String password = user.getPassword();

        User existingUser = ur.findByUsername(username);

        if (existingUser != null && existingUser.getPassword().equals(password)) {
            return existingUser;
        }
        return null;
    }

    // to add authorization step
    @Override
    public User deleteUserAsAdmin(User admin, int idToDelete) {
        try{
            User getAdmin = ur.findById(admin.getUser_id()).orElse(null);
            if (!getAdmin.getRole().equalsIgnoreCase("Admin")) {
                throw new SecurityException("Only users with role 'Admin' can delete accounts.");
            }

            User userToDelete = ur.findById(idToDelete).orElse(null);
            if (userToDelete != null && userToDelete.getUser_id() != 0) {
                ur.deleteById(idToDelete);
                return userToDelete;
            }
        } catch (SecurityException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User updateUser(User change) {
        int userId = change.getUser_id();
        User existingUser = ur.findById(userId).orElse(null);

        if (existingUser == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (change.getUsername() != null && change.getUsername() != existingUser.getUsername()) {
            existingUser.setUsername(change.getUsername());
        }

        if (change.getPassword() != null) {
            existingUser.setPassword(change.getPassword());
        }

        if (existingUser.getRole() != "Admin" && (existingUser.getRole() == "Customer" || change.getRole() == "Seller")) {
            throw new IllegalArgumentException("You are not allowed to change your role");
        }

        existingUser.setPassword(change.getPassword());
        return ur.save(change);
    }

    @Override
    public User deleteUser(User user) {
        try {
            User userInfo = ur.findById(user.getUser_id()).orElse(null);
//        int userId = user.getUser_id();
            if (userInfo != null) {
                ur.deleteById(user.getUser_id());
                return userInfo;
            }
        } catch (SecurityException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        return null;
    }
}