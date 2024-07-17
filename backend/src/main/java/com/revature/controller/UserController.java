package com.revature.controller;

import com.revature.exception.BadRequestException;
import com.revature.exception.ConflictException;
import com.revature.exception.UnauthorizedException;
import com.revature.model.User;
import com.revature.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint for registering a new User.
     *
     * @param user The User to be registered.
     * @return The persisted User including it's newly assigned userId, error message if bad request.
     */
    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody User user) {

        try {
            User newUser = userService.addUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (BadRequestException bre) {
            return new ResponseEntity<>(bre.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ConflictException ce) {
            return new ResponseEntity<>(ce.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable int userId) {
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }

    /**
     * Endpoint for updating a User given it's userId.
     *
     * @param userId The userId of a registered User.
     * @param user   containing User data to be updated.
     * @return The updated User.
     */
    @PutMapping
    public ResponseEntity<Object> updateUser(@PathVariable Integer userId, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(userId, user);
            return ResponseEntity.ok("Password updated");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Endpoint for deleting a User using that user's credentials.
     *
     * @param user containing valid username & password to delete their account.
     * @return The number of rows affected.
     */
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestBody User user) {

        try {
            userService.deleteUser(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UnauthorizedException ue) {
            return new ResponseEntity<>(ue.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>(iae.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint for deleting a User as an Admin.
     *
     * @param userId The userId of User to be deleted.
     * @param admin  containing valid username & password for an admin account.
     * @return The number of rows affected.
     */
    @DeleteMapping("admin/{userId}")
    public ResponseEntity<String> deleteUserAsAdmin(@PathVariable int userId, @RequestBody User admin) {

        try {
            userService.deleteUser(userId, admin);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UnauthorizedException ue) {
            return new ResponseEntity<>(ue.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>(iae.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // to add JWT

    /**
     * Endpoint for verifying a User login.
     *
     * @param user User containing a username/password combination to be verified.
     * @return The verified User.
     */
    @PostMapping("/login")
    public ResponseEntity<User> loginValidate(@RequestBody User user) {
        User validatedUser = userService.verifyUser(user);

        if (validatedUser != null) {
            return ResponseEntity.ok(validatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
