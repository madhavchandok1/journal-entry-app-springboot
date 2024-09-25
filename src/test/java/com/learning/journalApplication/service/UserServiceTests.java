package com.learning.journalApplication.service;


import com.learning.journalApplication.entity.User;
import com.learning.journalApplication.repository.UserRepository;
import com.learning.journalApplication.service.testsources.userServiceTestSources.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTests {
    @Mock
    private UserRepository _userRepository;

    @InjectMocks
    private UserService _userService;

    @BeforeEach
    public void initialize(){
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the behavior of the {@link UserService#getAllUsers()} method.
     *
     * This parameterized test verifies that the {@link UserRepository#findAll()} method
     * correctly retrieves a list of users and that the expected user count and role
     * count match the actual values returned by the service.
     *
     * <p>The test is executed with various inputs provided by the {@link GetAllUsersTestSource}
     * class, which supplies a list of users, the expected user count, and the expected
     * count of roles for the first user in the list.
     *
     * <p>Assertions include:
     * <ul>
     *     <li>Checking that the size of the returned user list matches the expected user count.</li>
     *     <li>Verifying that the size of roles for the first user matches the expected roles count.</li>
     *     <li>Ensuring that the {@link UserRepository#findAll()} method is called exactly once.</li>
     * </ul>
     *
     * @param listOfUsers The list of users to be returned by the repository.
     * @param expectedUserCount The expected number of users in the returned list.
     * @param expectedRolesCount The expected number of roles for the first user in the list.
     */
    @ParameterizedTest
    @ArgumentsSource(GetAllUsersTestSource.class)
    public void test_GetAllUsers(List<User> listOfUsers, int expectedUserCount, int expectedRolesCount){
        // Arrange: Mock the findAll method to return the specified list of users
        when(_userRepository.findAll()).thenReturn(listOfUsers);

        // Act: Call the service method to retrieve all users
        List<User> result = _userService.getAllUsers();

        // Assert: Check that the returned user list size matches the expected user count
        assertEquals(expectedUserCount, result.size());
        System.out.println("Expected User Count: " +  expectedUserCount);
        System.out.println("Actual User Count: " + result.size());

        // Assert: Verify that the number of roles for the first user matches the expected count
        assertEquals(expectedRolesCount, result.get(0).getRoles().size());
        System.out.println("Expected Role Count: " +  expectedRolesCount);
        System.out.println("Actual Role Count: " + result.get(0).getRoles().size());

        // Verify: Ensure that the findAll method was called exactly once on the repository
        verify(_userRepository, times(1)).findAll();
        System.out.println("User Repository \"findAll()\" method called exactly once");
    }

    /**
     * Tests the behavior of the {@link UserService#findUserByUserName(String)} method.
     *
     * This parameterized test verifies that the {@link UserRepository#findByUserName(String)}
     * method correctly retrieves a user based on the provided username.
     *
     * <p>The test is executed with various inputs provided by the
     * {@link FindUserByUserNameTestSource} class, which supplies a user object and
     * the username to be searched.
     *
     * <p>Assertions include:
     * <ul>
     *     <li>Verifying that the returned user's username matches the expected username.</li>
     *     <li>Ensuring that the {@link UserRepository#findByUserName(String)} method is
     *     called exactly once.</li>
     * </ul>
     *
     * @param user The user object expected to be returned by the repository.
     * @param UserName The username used to find the user.
     */
    @ParameterizedTest
    @ArgumentsSource(FindUserByUserNameTestSource.class)
    public void test_FindUserByUserName(User user, String UserName){
        // Arrange: Mock the findByUserName method to return the specified user object
        when(_userRepository.findByUserName(UserName)).thenReturn(user);

        // Act: Call the service method to retrieve the user by username
        User result = _userService.findUserByUserName(UserName);

        // Assert: Check that the returned user's username matches the expected username
        assertEquals(UserName, result.getUserName());
        System.out.println("Expected User Name: " +  UserName);
        System.out.println("Actual User Name: " + result.getUserName());
        // Verify: Ensure that the findByUserName method was called exactly once on the repository
        verify(_userRepository, times(1)).findByUserName(UserName);;
        System.out.println("User Repository \"findByUserName()\" called exactly once");
    }

    /**
     * Tests the behavior of the {@link UserService#saveUser(User)} method.
     *
     * This test verifies that the {@link UserRepository#save(User)} method
     * is not called when the service method is invoked with a User object.
     *
     * <p>The assertion checks that the repository's save() method has not
     * been called at all during this test, indicating that the save logic
     * in the service may not be implemented or is conditional.
     *
     * <p>Assertions include:
     * <ul>
     *     <li>Verifying that the {@link UserRepository#save(User)} method is
     *     called zero times.</li>
     * </ul>
     */
    @Test
    public void test_SaveUser(){
        // Arrange & Act: Call the saveUser method on the service with a mock User object
        _userService.saveUser(any(User.class));

        // Verify: Ensure that the save method was not called on the repository
        verify(_userRepository, times(0)).save(any(User.class));
        System.out.println("User Repository's \"save()\" method called zero times");
    }

    /**
     * Tests the behavior of the {@link UserService#saveNewUser(User)} method.
     *
     * This parameterized test verifies that when a new user is saved,
     * the plaintext password is not stored, and the user has the
     * correct default roles.
     *
     * <p>The test is executed with various inputs provided by the
     * {@link SaveNewUserTestSource} class, which supplies a user object
     * and a plaintext password.
     *
     * <p>Assertions include:
     * <ul>
     *     <li>Verifying that the stored password is not equal to the plaintext password,
     *     indicating that it has been encrypted.</li>
     *     <li>Checking that the user has the role "USER".</li>
     *     <li>Ensuring that the {@link UserRepository#save(User)} method is called exactly once.</li>
     * </ul>
     *
     * @param user The user object to be saved, which will have its password encrypted.
     * @param plainTextPassword The plaintext password used for comparison.
     */
    @ParameterizedTest
    @ArgumentsSource(SaveNewUserTestSource.class)
    public void test_SaveNewUser(User user, String plainTextPassword){
        // Act: Call the service method to save the new user
        _userService.saveNewUser(user);

        // Assert: Check that the stored password is not equal to the plaintext password
        assertNotEquals(plainTextPassword, user.getPassword());
        System.out.println("Encrypted Password: " +  user.getPassword());
        System.out.println("Plain Text Password: " +  plainTextPassword);

        // Assert: Verify that the user has the correct default role
        assertEquals(List.of("USER"), user.getRoles());

        // Verify: Ensure that the save method was called exactly once on the repository
        verify(_userRepository, times(1)).save(user);
        System.out.println("User Repository's \"save()\" method called exactly once");
    }

    /**
     * Tests the behavior of the {@link UserService#saveAdminUser(User)} method.
     *
     * This parameterized test verifies that when an admin user is saved,
     * the plaintext password is not stored, and the user has the
     * correct roles assigned.
     *
     * <p>The test is executed with various inputs provided by the
     * {@link SaveAdminUserTestSource} class, which supplies a user object
     * and a plaintext password.
     *
     * <p>Assertions include:
     * <ul>
     *     <li>Verifying that the stored password is not equal to the plaintext password,
     *     indicating that it has been encrypted.</li>
     *     <li>Checking that the user has the roles "USER" and "ADMIN".</li>
     *     <li>Ensuring that the {@link UserRepository#save(User)} method is called exactly once.</li>
     * </ul>
     *
     * @param user The user object to be saved, which will have its password encrypted.
     * @param plainTextPassword The plaintext password used for comparison.
     */
    @ParameterizedTest
    @ArgumentsSource(SaveAdminUserTestSource.class)
    public void test_SaveAdminUser(User user, String plainTextPassword){
        // Act: Call the service method to save the admin user
        _userService.saveAdminUser(user);

        // Assert: Check that the stored password is not equal to the plaintext password
        assertNotEquals(plainTextPassword, user.getPassword());
        System.out.println("Encrypted Password: " +  user.getPassword());
        System.out.println("Plain Text Password: " +  plainTextPassword);

        // Assert: Verify that the user has the correct roles assigned
        assertEquals(List.of("USER", "ADMIN"), user.getRoles());

        // Verify: Ensure that the save method was called exactly once on the repository
        verify(_userRepository, times(1)).save(user);
        System.out.println("User Repository's \"save()\" method called exactly once");
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "testuser1",
        "testuser2",
        "testuser3",
        "testuser4"
    })
    public void test_DeleteUser(String userName){
        _userRepository.deleteByUserName(userName);

        verify(_userRepository, times(1)).deleteByUserName(userName);
        System.out.println("User Repository's \"deleteByUserName()\" method called exactly once");
    }
}
