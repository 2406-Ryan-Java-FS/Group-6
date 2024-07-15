package com.revature.service;

import com.revature.model.User;

public interface IUserService {

    public User addUser(User user);
    public User getUser(int id);
    public User loginValidate(User user);
    public User deleteUserAsAdmin(User admin, int idToDelete);
    public User deleteUser(User user);
    public User updateUser(User change);

}
