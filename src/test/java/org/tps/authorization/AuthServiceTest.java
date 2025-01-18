package org.tps.authorization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthServiceTest {
    private static final String VALID_USERNAME = "testuser";
    private static final String INVALID_USERNAME = "unknownuser";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "test@example.com";
    private static final String PHONE_NUMBER = "1234567890";

    private AuthService authService;
    private UserDao userDao;

    @BeforeEach
    public void setup() {
        userDao = mock(UserDao.class);
        authService = new AuthService(userDao);
    }

    @Test
    public void testAuthenticate_Success() {
        String hashedPassword = BCrypt.hashpw(PASSWORD, BCrypt.gensalt());
        User mockUser = new User(1, VALID_USERNAME, hashedPassword, EMAIL, PHONE_NUMBER);
        when(userDao.getUserByUsername(VALID_USERNAME)).thenReturn(Optional.of(mockUser));

        boolean result = authService.authenticate(VALID_USERNAME, PASSWORD);

        assertTrue(result, "Аутентификация должна быть успешной для правильного имени пользователя и пароля");
        verify(userDao, times(1)).getUserByUsername(VALID_USERNAME);
    }

    @Test
    public void testAuthenticate_Failure_WrongPassword() {
        String hashedPassword = BCrypt.hashpw("differentPassword", BCrypt.gensalt());
        User mockUser = new User(1, VALID_USERNAME, hashedPassword, EMAIL, PHONE_NUMBER);
        when(userDao.getUserByUsername(VALID_USERNAME)).thenReturn(Optional.of(mockUser));

        boolean result = authService.authenticate(VALID_USERNAME, PASSWORD);

        assertFalse(result, "Аутентификация должна быть неуспешной для неправильного пароля");
        verify(userDao, times(1)).getUserByUsername(VALID_USERNAME);
    }

    @Test
    public void testAuthenticate_Failure_UserNotFound() {
        when(userDao.getUserByUsername(INVALID_USERNAME)).thenReturn(Optional.empty());

        boolean result = authService.authenticate(INVALID_USERNAME, PASSWORD);

        assertFalse(result, "Аутентификация должна быть неуспешной для неизвестного имени пользователя");
        verify(userDao, times(1)).getUserByUsername(INVALID_USERNAME);
    }

    @Test
    public void testRegister_Success() {
        // Предполагаем, что сохранение пользователя проходит успешно
        doNothing().when(userDao).saveUser(any(User.class));

        authService.register(VALID_USERNAME, PASSWORD, EMAIL, PHONE_NUMBER);

        // Захватываем аргумент, переданный в saveUser
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDao, times(1)).saveUser(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals(VALID_USERNAME, savedUser.getUsername(), "Имя пользователя должно соответствовать переданному");
        assertEquals(EMAIL, savedUser.getEmail(), "Email должен соответствовать переданному");
        assertEquals(PHONE_NUMBER, savedUser.getPhoneNumber(), "Номер телефона должен соответствовать переданному");

        // Проверяем, что пароль был захеширован и отличается от исходного
        assertNotEquals(PASSWORD, savedUser.getPassword(), "Пароль должен быть захеширован и не должен совпадать с исходным");
        assertTrue(BCrypt.checkpw(PASSWORD, savedUser.getPassword()), "Хешированный пароль должен соответствовать исходному паролю");
    }

    @Test
    public void testRegister_PasswordIsHashed() {
        // Arrange
        doNothing().when(userDao).saveUser(any(User.class));

        authService.register(VALID_USERNAME, PASSWORD, EMAIL, PHONE_NUMBER);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDao).saveUser(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        String hashedPassword = savedUser.getPassword();

        assertNotNull(hashedPassword, "Хешированный пароль не должен быть null");
        assertNotEquals(PASSWORD, hashedPassword, "Хешированный пароль должен отличаться от исходного пароля");
        assertTrue(BCrypt.checkpw(PASSWORD, hashedPassword), "Хешированный пароль должен соответствовать исходному паролю");
    }

    @Test
    public void testRegister_DuplicateUsername() {
        // Предполагаем, что сохранение пользователя выбрасывает исключение при дублировании имени пользователя
        doThrow(new RuntimeException("Duplicate username")).when(userDao).saveUser(any(User.class));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.register(VALID_USERNAME, PASSWORD, EMAIL, PHONE_NUMBER);
        }, "Ожидается исключение при попытке зарегистрировать пользователя с уже существующим именем");

        assertEquals("Duplicate username", exception.getMessage(), "Сообщение исключения должно соответствовать ожидаемому");
        verify(userDao, times(1)).saveUser(any(User.class));
    }
}

