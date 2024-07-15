package com.example.library.controller;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.library.controller.UserController;
import com.example.library.entity.User;
import com.example.library.exception.UserNotFoundException;
import com.example.library.service.CustomUserDetailsService;
import com.example.library.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @InjectMocks
    private UserController userController;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User(1,"testuser", "password", List.of("USER"));
        testUser.setUserId(1L);
    }

    @Test
    void testRegisterUser_Success() {
        when(userDetailsService.save(any(User.class))).thenReturn(testUser);

        ResponseEntity<User> response = userController.registerUser(testUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUser, response.getBody());
    }

    @Test
    void testCreateUser_Success() {
        when(userService.createUser(any(User.class))).thenReturn(testUser);

        ResponseEntity<?> response = userController.createUser(testUser);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testUser, response.getBody());
    }

    @Test
    void testCreateUser_Failure() {
        when(userService.createUser(any(User.class))).thenThrow(new RuntimeException("Invalid user details"));

        ResponseEntity<?> response = userController.createUser(testUser);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Failed to create user"));
    }


    @Test
    void testGetUserById_Success() {
        when(userService.getUserById(anyLong())).thenReturn(testUser);

        ResponseEntity<?> response = userController.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUser, response.getBody());
    }

    @Test
    void testGetUserById_UserNotFound() {
        when(userService.getUserById(anyLong())).thenThrow(new UserNotFoundException("User not found"));

        ResponseEntity<?> response = userController.getUserById(2L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("No User exists with this userId"));
    }

    @Test
    void testGetUserByUserName_Success() {
        when(userService.getUserByUserName(anyString())).thenReturn(testUser);

        ResponseEntity<?> response = userController.getUserByUserName("testuser");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUser, response.getBody());
    }

    @Test
    void testGetUserByUserName_UserNotFound() {
        when(userService.getUserByUserName(anyString())).thenThrow(new UserNotFoundException("User not found"));

        ResponseEntity<?> response = userController.getUserByUserName("nonexistentuser");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("No User exists with this userName"));
    }

    @Test
    void testGetUsers_Success() {
        List<User> users = new ArrayList<>();
        users.add(testUser);

        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<?> response = userController.getUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    void testGetUsers_NoUsersFound() {
        when(userService.getAllUsers()).thenReturn(new ArrayList<>());

        ResponseEntity<?> response = userController.getUsers();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("No existing Users"));
    }


    @Test
    void testUpdateUser_Success() {
        when(userService.getUserById(anyLong())).thenReturn(testUser);
        when(userService.createUser(any(User.class))).thenReturn(testUser);

        User updatedUser = new User(1, "updateduser", "newpassword", List.of("ADMIN"));
        updatedUser.setUserId(1L);

        ResponseEntity<?> response = userController.updateUser(updatedUser, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUser, response.getBody());
    }

    @Test
    void testUpdateUser_UserNotFound() {
        when(userService.getUserById(anyLong())).thenReturn(null);

        User updatedUser = new User(1,"updateduser", "newpassword", List.of("ADMIN"));
        updatedUser.setUserId(1L);

        ResponseEntity<?> response = userController.updateUser(updatedUser, 1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("No User exists with this userId"));
    }


    @Test
    void testDeleteUserById_Success() {
        when(userService.deleteUserById(anyLong())).thenReturn(true);

        ResponseEntity<?> response = userController.deleteUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("User deleted Successfully"));
    }

    @Test
    void testDeleteUserById_UserNotFound() {
        when(userService.deleteUserById(anyLong())).thenReturn(false);

        ResponseEntity<?> response = userController.deleteUserById(2L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("No User exists with this userId"));
    }


    @Test
    void testDeleteUserByName_Success() {
        when(userService.deleteUserByName(anyString())).thenReturn(true);

        ResponseEntity<?> response = userController.deleteUserByName("testuser");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("User deleted Successfully"));
    }

    @Test
    void testDeleteUserByName_UserNotFound() {
        when(userService.deleteUserByName(anyString())).thenReturn(false);

        ResponseEntity<?> response = userController.deleteUserByName("nonexistentuser");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("No User exists with this userName"));
    }
}
