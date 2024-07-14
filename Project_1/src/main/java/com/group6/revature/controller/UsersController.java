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

    @DeleteMapping("admin/{id}")
    public ResponseEntity<Users> deleteUserAsAdmin(@PathVariable("id") int idToDelete, @RequestBody Users admin) {
        Users adminDeleter = us.deleteUserAsAdmin(admin, idToDelete);

        if (adminDeleter != null) {
            return ResponseEntity.ok(adminDeleter);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

    }

//    @PostMapping(consumes = "application/json", produces = "application/json")
    @PostMapping
    public ResponseEntity<Users> addUser(@RequestBody Users u) {
        try {
            u = us.addUser(u);
            return new ResponseEntity<>(u, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody Users change) {
        try {
            us.updateUser(change);
            return ResponseEntity.ok("Password updated");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }



    // to add JWT
    @PostMapping("/login")
    public ResponseEntity<Users> loginValidate(@RequestBody Users user) {
        Users validatedUser = us.loginValidate(user);

        if (validatedUser != null) {
            return ResponseEntity.ok(validatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

    }

    @DeleteMapping
    public ResponseEntity<Users> deleteUser(@RequestBody Users user) {
        Users deletedUser = us.deleteUser(user);

        if (deletedUser != null) {
            return ResponseEntity.ok(deletedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

    }



}
