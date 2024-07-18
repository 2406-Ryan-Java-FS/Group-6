package com.revature.controller;

import com.revature.exception.BadRequestException;
import com.revature.exception.ConflictException;
import com.revature.exception.UnauthorizedException;
import com.revature.model.User;
import com.revature.repository.UserRepository;
import com.revature.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    UserService userService;
    UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
     * Endpoint for registering a new User.
     *
     * @param user The User to be registered.
     * @return If successful, returns the persisted User with its newly assigned userId and createdAt, along with a 201 status code.
     * If unsuccessful, returns a String message indicating the failure reason, along with a 400 or 409 status code.
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

    /**
     * Endpoint for retrieving a User given its userId.
     *
     * @param userId The userId of the User to retrieve.
     * @return If successful, returns the User along with a 200 status code.
     * If unsuccessful, returns a String message indicating the failure reason along with a 400 status code.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable int userId) {

        try {
            User existingUser = userService.getUser(userId);
            return new ResponseEntity<>(existingUser, HttpStatus.OK);
        } catch (BadRequestException bre) {
            return new ResponseEntity<>(bre.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint for retrieving a User by passing bearer token through header
     */
    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint for updating a User given its userId.
     *
     * @param userId The userId of the registered User to be updated.
     * @param user   The User object containing the updated data.
     * @return If successful, returns the updated User.
     * If unsuccessful, returns a String message indicating the failure reason along with a 400, 401, or 409 status code.
     */
    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable Integer userId, @RequestBody User user) {

        try {
            User updatedUser = userService.updateUser(userId, user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (BadRequestException bre) {
            return new ResponseEntity<>(bre.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UnauthorizedException ue) {
            return new ResponseEntity<>(ue.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (ConflictException ce) {
            return new ResponseEntity<>(ce.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /**
     * Endpoint for deleting a User using that user's credentials.
     *
     * @param user The User object containing the valid username and password to delete their account.
     * @return If successful, returns a 200 status code.
     * If unsuccessful, returns a String message indicating the failure reason along with a 400 or 401 status code.
     */
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestBody User user) {

        try {
            userService.deleteUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>(iae.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UnauthorizedException ue) {
            return new ResponseEntity<>(ue.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Endpoint for deleting a User as an Admin.
     *
     * @param userId The userId of User to be deleted.
     * @param admin  containing valid username & password for an admin account.
     * @return If successful, returns a 200 status code.
     * If unsuccessful, returns a String message indicating the failure reason along with a 400 or 401 status code.
     */
    @DeleteMapping("admin/{userId}")
    public ResponseEntity<String> deleteUserAsAdmin(@PathVariable int userId, @RequestBody User admin) {

        try {
            userService.deleteUser(userId, admin);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>(iae.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UnauthorizedException ue) {
            return new ResponseEntity<>(ue.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

}
