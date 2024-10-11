package com.growthx.Task.controller;

import com.growthx.Task.model.User;
import com.growthx.Task.services.Impl.AssignmentService;
import com.growthx.Task.services.Impl.UserService;
import com.growthx.Task.services.JWTServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private UserService userService;
    private final JWTServices Jwt;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            userService.registerAdmin(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Admin registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Admin Already Exist!");
        }
    }
    @GetMapping("/assignments")
    public ResponseEntity<?> getAssignments(@RequestHeader(name = "Authorization") String token) {
        try {
            String adminEmail= Jwt.extractUserName(token.substring(7));
            User admin = userService.findUser(adminEmail);
            return ResponseEntity.ok(assignmentService.getAssignmentsForAdmin(admin.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!");
        }
    }

    @PostMapping("/assignments/{id}/accept")
    public ResponseEntity<?> acceptAssignment(@PathVariable String id) {
        return ResponseEntity.ok(assignmentService.acceptAssignment(id));
    }

    @PostMapping("/assignments/{id}/reject")
    public ResponseEntity<?> rejectAssignment(@PathVariable String id) {
        return ResponseEntity.ok(assignmentService.rejectAssignment(id));
    }
}
