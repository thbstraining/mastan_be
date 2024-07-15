package com.example.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.library.entity.User;
import com.example.library.exception.UserNotFoundException;
import com.example.library.service.CustomUserDetailsService;
import com.example.library.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/user/reg")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User savedUser = userDetailsService.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/user/create-user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User savedUser = userService.createUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable long userId) {
        try {
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No User exists with this userId : " + userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }

    @GetMapping("/name/{userName}")
    public ResponseEntity<?> getUserByUserName(@PathVariable String userName) {
        try {
            User user = userService.getUserByUserName(userName);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No User exists with this userName : " + userName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getUsers() {
        try {
            List<User> users = userService.getAllUsers();
            if (!users.isEmpty()) {
                return new ResponseEntity<>(users, HttpStatus.OK);
            }
            return new ResponseEntity<>("No existing Users in the database", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }

    @PatchMapping("/user/update/{userId}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable long userId) {
        try {
            User oldUser = userService.getUserById(userId);
            if (oldUser != null) {
                oldUser.setUserId(userId);
                oldUser.setUserName(user.getUserName());
                oldUser.setPassword(user.getPassword());
                oldUser.setRoles(user.getRoles());
                userService.createUser(oldUser);
                return new ResponseEntity<>(oldUser, HttpStatus.OK);
            }
            return new ResponseEntity<>("No User exists with this userId : " + userId + " to update the User", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteUserId/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable long userId) {
        try {
            Boolean deletedValue = userService.deleteUserById(userId);
            if (deletedValue) {
                return new ResponseEntity<>("User deleted Successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("No User exists with this userId : " + userId, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteUser/{userName}")
    public ResponseEntity<?> deleteUserByName(@PathVariable String userName) {
        try {
            Boolean deletedValue = userService.deleteUserByName(userName);
            if (deletedValue) {
                return new ResponseEntity<>("User deleted Successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("No User exists with this userName : " + userName, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }
}
