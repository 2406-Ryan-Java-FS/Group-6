package com.revature.controller;

import com.revature.model.User;
import com.revature.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    IUserService us;

    @Autowired
    public UserController(IUserService us) { this.us = us; }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) { return us.getUser(id); }

    @DeleteMapping("admin/{id}")
    public ResponseEntity<User> deleteUserAsAdmin(@PathVariable("id") int idToDelete, @RequestBody User admin) {
        User adminDeleter = us.deleteUserAsAdmin(admin, idToDelete);

        if (adminDeleter != null) {
            return ResponseEntity.ok(adminDeleter);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

    }

//    @PostMapping(consumes = "application/json", produces = "application/json")
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User u) {
        try {
            u = us.addUser(u);
            return new ResponseEntity<>(u, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody User change) {
        try {
            us.updateUser(change);
            return ResponseEntity.ok("Password updated");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }



    // to add JWT
    @PostMapping("/login")
    public ResponseEntity<User> loginValidate(@RequestBody User user) {
        User validatedUser = us.loginValidate(user);

        if (validatedUser != null) {
            return ResponseEntity.ok(validatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

    }

    @DeleteMapping
    public ResponseEntity<User> deleteUser(@RequestBody User user) {
        User deletedUser = us.deleteUser(user);

        if (deletedUser != null) {
            return ResponseEntity.ok(deletedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

    }



}
