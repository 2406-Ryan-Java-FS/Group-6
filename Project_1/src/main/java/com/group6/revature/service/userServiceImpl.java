package com.group6.revature.service;

import com.group6.revature.model.Users;
import org.springframework.beans.factory.annotation.Autowired;

import com.group6.revature.repository.userRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class userServiceImpl implements userService{

    @Autowired
    userRepo ur;


    @Override
    public Users addUser(Users user) {
        Users existingUser = ur.findByUsername(user.getUsername());
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
    public Users getUser(int id) {
        return ur.findById(id).orElse(null);
    }

    @Override
    public Users loginValidate(Users user) {
        String username = user.getUsername();
        String password = user.getPassword();

        Users existingUser = ur.findByUsername(username);

        if (existingUser != null && existingUser.getPassword().equals(password)) {
            return existingUser;
        }
        return null;
    }

    // to add authorization step
    @Override
    public Users deleteUserAsAdmin(Users admin, int idToDelete) {
        try{
            Users getAdmin = ur.findById(admin.getUser_id()).orElse(null);
            if (!getAdmin.getRole().equalsIgnoreCase("Admin")) {
                throw new SecurityException("Only users with role 'Admin' can delete accounts.");
            }

            Users userToDelete = ur.findById(idToDelete).orElse(null);
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
    public Users updateUser(Users change) {
        int userId = change.getUser_id();
        Users existingUser = ur.findById(userId).orElse(null);

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
    public Users deleteUser(Users user) {
        try {
            Users userInfo = ur.findById(user.getUser_id()).orElse(null);
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