package com.group6.revature.controller;

import com.group6.revature.model.Users;
import com.group6.revature.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UsersController {

    userService us;

    @Autowired
    public UsersController(userService us) { this.us = us; }

    @GetMapping("/{id}")
    public Users getUser(@PathVariable int id) { return us.getUser(id); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Users> deleteUser(@PathVariable("id") int idToDelete, @RequestBody Users admin) {
        Users adminDeleter = us.deleteUser(admin, idToDelete);

        if (adminDeleter != null) {
            return ResponseEntity.ok(adminDeleter);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

    }


}
