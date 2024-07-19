package com.revature.service;

import com.revature.dto.UserUpdateDTO;
import com.revature.model.User;

public interface IUserService {

    User addUser(User user);

    User getUser(Integer userId);

    User updateUser(String username, UserUpdateDTO userUpdateDTO);

    void deleteUser(User user);

    void deleteUser(int userId, User admin);

    User verifyUser(User user);

}
