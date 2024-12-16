package org.tps.autorization;

import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class AuthServiceTest {
    private static final String VALID_USERNAME = "testuser";
    private static final String INVALID_USERNAME = "unknownuser";
    private static final String PASSWORD = "1234";
    private static final String HASHED_PASSWORD = "$2a$10$5DwJeN9k7CM0XefYGoMR3uuvDwwTCoO9KPO1DsJ5OqDpFjNdW1q3K";
    private static final String EMAIL = "test@phystech.edu";
    private static final String PHONE = "88002502525";

    @Test
    void testAuthenticate_Success() {
        // Arrange
        UserDao userDao = mock(UserDao.class);
        User mockUser = new User(1, VALID_USERNAME, HASHED_PASSWORD, EMAIL, PHONE);
        when(userDao.getUserByUsername(VALID_USERNAME)).thenReturn(Optional.of(mockUser));
        AuthService authService = new AuthService(userDao);
        boolean result = authService.authenticate(VALID_USERNAME, PASSWORD);
        assertTrue(result);
        verify(userDao, times(1)).getUserByUsername(VALID_USERNAME);
    }

    @Test
    void testAuthenticate_Failure() {
        // Arrange
        UserDao userDao = mock(UserDao.class);
        when(userDao.getUserByUsername(INVALID_USERNAME)).thenReturn(Optional.empty());
        AuthService authService = new AuthService(userDao);
        boolean result = authService.authenticate(INVALID_USERNAME, PASSWORD);
        assertFalse(result);
        verify(userDao, times(1)).getUserByUsername(INVALID_USERNAME);
    }

    @Test
    void testRegister() {
        UserDao userDao = mock(UserDao.class);
        AuthService authService = new AuthService(userDao);
        authService.register(VALID_USERNAME, PASSWORD, EMAIL, PHONE);
        verify(userDao, times(1)).saveUser(any(User.class));
    }
}