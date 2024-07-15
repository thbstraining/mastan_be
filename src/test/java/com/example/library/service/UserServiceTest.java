package com.example.library.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.library.entity.User;
import com.example.library.exception.UserNotFoundException;
import com.example.library.repository.UserRepository;
import com.example.library.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User(1,"testuser", "password", List.of("USER"));
        testUser.setUserId(1L);
    }

    @Test
    void testCreateUser_Success() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User savedUser = userService.createUser(testUser);

        assertNotNull(savedUser);
        assertEquals(testUser.getUserName(), savedUser.getUserName());
    }

    @Test
    void testCreateUser_NullUser() {
        assertThrows(UserNotFoundException.class, () -> {
            userService.createUser(null);
        });
    }


    @Test
    void testGetUserById_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));

        User fetchedUser = userService.getUserById(1L);

        assertNotNull(fetchedUser);
        assertEquals(testUser.getUserName(), fetchedUser.getUserName());
    }

    @Test
    void testGetUserById_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(2L);
        });
    }

    @Test
    void testGetUserByUserName_Success() {
        when(userRepository.findByUserName(anyString())).thenReturn(testUser);

        User fetchedUser = userService.getUserByUserName("testuser");

        assertNotNull(fetchedUser);
        assertEquals(testUser.getUserId(), fetchedUser.getUserId());
    }

    @Test
    void testGetUserByUserName_UserNotFound() {
        when(userRepository.findByUserName(anyString())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserByUserName("nonexistentuser");
        });
    }

    @Test
    void testGetAllUsers_Success() {
        List<User> userList = new ArrayList<>();
        userList.add(testUser);

        when(userRepository.findAll()).thenReturn(userList);

        List<User> fetchedUsers = userService.getAllUsers();

        assertEquals(1, fetchedUsers.size());
        assertEquals(testUser.getUserName(), fetchedUsers.get(0).getUserName());
    }

    @Test
    void testUpdateUserById_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User updatedUser = new User(1,"updateduser", "newpassword", List.of("ADMIN"));
        updatedUser.setUserId(1L);

        User savedUser = userService.updateUserById(updatedUser, 1L);

        assertNotNull(savedUser);
        assertEquals(updatedUser.getUserName(), savedUser.getUserName());
    }

    @Test
    void testUpdateUserById_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        User updatedUser = new User(1,"updateduser", "newpassword", List.of("ADMIN"));
        updatedUser.setUserId(1L);

        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUserById(updatedUser, 1L);
        });
    }

 
    @Test
    void testDeleteUserById_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));

        boolean result = userService.deleteUserById(1L);

        assertTrue(result);
        verify(userRepository, times(1)).delete(testUser);
    }

    @Test
    void testDeleteUserById_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUserById(2L);
        });
    }

    @Test
    void testDeleteUserByName_Success() {
        when(userRepository.findByUserName(anyString())).thenReturn(testUser);

        boolean result = userService.deleteUserByName("testuser");

        assertTrue(result);
        verify(userRepository, times(1)).delete(testUser);
    }

    @Test
    void testDeleteUserByName_UserNotFound() {
        when(userRepository.findByUserName(anyString())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUserByName("nonexistentuser");
        });
    }
}
