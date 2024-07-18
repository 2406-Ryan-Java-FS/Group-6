package com.revature.service;

import com.revature.model.User;

public interface IUserService {

    User addUser(User user);

    User getUser(Integer userId);

    User updateUser(Integer userId, User user);

    void deleteUser(User user);

    void deleteUser(int userId, User admin);

    User verifyUser(User user);

}
