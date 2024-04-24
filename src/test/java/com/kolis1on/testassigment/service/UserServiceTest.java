package com.kolis1on.testassigment.service;

import com.kolis1on.testassigment.entity.User;
import com.kolis1on.testassigment.exception.DatesAreNotValidException;
import com.kolis1on.testassigment.exception.UserIsYoungerException;
import com.kolis1on.testassigment.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private List<User> mockUsers;

    @BeforeEach
    public void setup() {
        mockUsers = new ArrayList<>();
    }

    @Test
    public void testCreateUser_ValidAge() throws Exception {
        when(userRepository.getUsers()).thenReturn(mockUsers);
        User user = new User();
        user.setBirthDate("2000-01-01");
        userService.createUser(user);
        assertTrue(mockUsers.contains(user));
    }

    @Test
    public void testCreateUser_YoungUser() {
        User user = new User();
        user.setBirthDate("2023-01-01");
        assertThrows(UserIsYoungerException.class, () -> userService.createUser(user));
    }



    @Test
    public void testUpdateUser() {
        when(userRepository.getUsers()).thenReturn(mockUsers);
        User existingUser = new User();
        existingUser.setEmail("test@example.com");
        existingUser.setBirthDate("1990-01-01");

        mockUsers.add(existingUser);

        User updatedUser = new User();
        updatedUser.setEmail("test@example.com");
        updatedUser.setBirthDate("2000-01-01");

        userService.updateUser("test@example.com", updatedUser);

        assertEquals("2000-01-01", mockUsers.get(0).getBirthDate());
    }

    @Test
    public void testDeleteUserByEmail() {
        when(userRepository.getUsers()).thenReturn(mockUsers);
        User userToDelete = new User();
        userToDelete.setEmail("test@example.com");
        userToDelete.setBirthDate("1990-01-01");

        mockUsers.add(userToDelete);

        userService.deleteUserByEmail("test@example.com");

        assertTrue(mockUsers.isEmpty());
    }



    @Test
    public void testFindByBirthRange_InvalidRange() {
        assertThrows(DatesAreNotValidException.class, () -> userService.findByBirthRange("2000-01-01", "1990-01-01"));
    }
}
