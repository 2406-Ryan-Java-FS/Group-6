package com.revature.controller;

import com.revature.dto.AuthResponseDTO;
import com.revature.dto.UserUpdateDTO;
import com.revature.exception.BadRequestException;
import com.revature.exception.ConflictException;
import com.revature.exception.UnauthorizedException;
import com.revature.model.User;
import com.revature.repository.UserRepository;
import com.revature.security.JwtGenerator;
import com.revature.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    UserService userService;
    UserRepository userRepository;
    JwtGenerator jwtGenerator;
    AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, JwtGenerator jwtGenerator, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.jwtGenerator = jwtGenerator;
        this.authenticationManager = authenticationManager;
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
     * @param UserUpdateDTO has usernameNew and passwordNew fields
     *                      Will invalidate the token if pass empty field; Only pass field to change
     * @param authentication will update that account's username and or password
     * @return If successful, returns the updated User.
     * If unsuccessful, returns a String message indicating the failure reason along with a 400, 401, or 409 status code.
     */
    @PutMapping
    public ResponseEntity<Object> updateUser(Authentication authentication, @RequestBody UserUpdateDTO userUpdateDTO) {

        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String username = userDetails.getUsername();
            User updatedUser = userService.updateUser(username, userUpdateDTO);

            // Generate a new JWT token based on new credentials
            Authentication newAuthentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(updatedUser.getUsername(), updatedUser.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(newAuthentication);
            String newToken = jwtGenerator.generateToken(newAuthentication);

            // Set the new token in the response headers
            AuthResponseDTO response = new AuthResponseDTO(newToken);

            return new ResponseEntity<>(response, HttpStatus.OK);
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
     * @param authentication deletes user tied to the token.
     * @return If successful, returns a 200 status code.
     * If unsuccessful, returns a String message indicating the failure reason along with a 400 or 401 status code.
     */
    @DeleteMapping
    public ResponseEntity<String> deleteUser(Authentication authentication) {

        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }

            userService.deleteUser(user.getUserId());
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
     * @param authentication checks if token has admin role.
     * @return If successful, returns a 200 status code.
     * If unsuccessful, returns a String message indicating the failure reason along with a 400 or 401 status code.
     */
    @DeleteMapping("admin/{userId}")
    public ResponseEntity<String> deleteUserAsAdmin(@PathVariable int userId, Authentication authentication) {

        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            User admin = userRepository.findByUsername(username);

            if (!admin.getRole().equalsIgnoreCase("Admin")) {
                throw new UnauthorizedException("Only Admins can do this");
            }

            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>(iae.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UnauthorizedException ue) {
            return new ResponseEntity<>(ue.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

}
