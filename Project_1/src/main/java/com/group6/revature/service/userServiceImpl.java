package com.group6.revature.service;

import com.group6.revature.model.Users;
import org.springframework.beans.factory.annotation.Autowired;

import com.group6.revature.repository.userRepo;

public class userServiceImpl implements userService{

    @Autowired
    userRepo ur;


    @Override
    public Users addUser(Users user) {
        return ur.save(user);
    }

    @Override
    public Users getUser(int id) {
        return ur.findById(id).orElse(null);
    }

    @Override
    public Users loginValidate(Users user) {

        int userId = user.getUser_id();
        Users existingUser = ur.findById(userId).orElse(null);

        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            return existingUser;
        }
        return null;
    }

    // to add authorization step
    @Override
    public Users deleteUser(Users admin, int idToDelete) {
        try{
            if (!admin.getRole().equalsIgnoreCase("Admin")) {
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
    public Users updatePassword(Users change) {
        return ur.save(change);
    }
}
