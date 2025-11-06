package com.practice.localiza.controller;

import com.practice.localiza.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.practice.localiza.auth.Login;
import com.practice.localiza.service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Login loginRequest) {
        try {
            User registeredUserLogin = userService.registerUser(loginRequest);
            return ResponseEntity.ok(registeredUserLogin);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/role/{userId}")
    public ResponseEntity<User> updateRole(
            @PathVariable Long userId,
            @RequestParam String role) {
        try {
            User updatedUserLogin = userService.updateRole(userId, role);
            return ResponseEntity.ok(updatedUserLogin);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}