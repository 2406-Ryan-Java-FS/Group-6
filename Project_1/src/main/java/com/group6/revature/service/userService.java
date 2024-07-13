package com.group6.revature.service;

import com.group6.revature.model.Users;

import java.util.Optional;

public interface userService {

    public Users addUser(Users user);
    public Users getUser(int id);
    public Users loginValidate(Users user);
    public Users deleteUser(Users admin, int idToDelete);
    public Users updatePassword(Users change);

}
