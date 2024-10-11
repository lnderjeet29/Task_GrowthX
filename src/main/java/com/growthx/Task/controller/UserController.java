package com.growthx.Task.controller;

import com.growthx.Task.dto.LoginRequest;
import com.growthx.Task.model.Assignment;
import com.growthx.Task.model.User;
import com.growthx.Task.services.Impl.AssignmentService;
import com.growthx.Task.services.Impl.UserService;
import com.growthx.Task.services.JWTServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AssignmentService assignmentService;
    private final JWTServices Jwt;
    /**
     * Register a new user.
     *
     * @param user User object containing registration information.
     * @return ResponseEntity with a success message.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User Already Exist!");
        }
    }
    /**
     * Login user. This is an example for how login might work. You can integrate it with JWT authentication.
     *
     * @param loginRequest Object containing username and password.
     * @return ResponseEntity with login status (JWT or success message).
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            String token = userService.loginUser(loginRequest);
            return ResponseEntity.ok("Login successful. Token: " + token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials. Login failed.");
        }
    }

    /**
     * Upload an assignment by the user.
     *
     * @param assignment Assignment object containing task details.
     * @param authentication Injected authentication token containing the current user's information.
     * @return ResponseEntity with the uploaded assignment.
     */
    @PostMapping("/upload")
    public ResponseEntity<?> submitAssignment(@RequestBody Assignment assignment, @RequestHeader(name = "Authorization") String token) {
        try {
            String userId = Jwt.extractUserName(token.substring(7)); // Get the username (or user email) from the Authentication token
            assignment.setUserId(userService.findUser(userId).getId());
            Assignment savedAssignment = assignmentService.submitAssignment(assignment);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAssignment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while uploading the assignment.");
        }
    }

    /**
     * Fetch all admins.
     *
     * @return ResponseEntity containing the list of all registered admins.
     */
    @GetMapping("/admins")
    public ResponseEntity<?> getAllAdmins() {
        try {
            List<User> admins = userService.getAllAdmins();
            if (admins.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No admins found.");
            }
            return ResponseEntity.ok(admins);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching admins.");
        }
    }
}

