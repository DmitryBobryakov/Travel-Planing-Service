package org.tps.autorization;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {
    private UserDao userDao;

    @BeforeEach
    public void setup() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("");

        userDao = new UserDao(dataSource);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            // Очистка таблицы перед каждым тестом для избежания конфликтов
            statement.execute("DROP TABLE IF EXISTS user_data");

            statement.execute("CREATE TABLE user_data (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(50) UNIQUE NOT NULL, " +
                    "password VARCHAR(255) NOT NULL, " +
                    "email VARCHAR(100) UNIQUE NOT NULL, " +
                    "phoneNumber VARCHAR(20))");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSaveAndRetrieveUser() {
        // Создаём пользователя
        User user = new User(0, "testuser", "hashedPassword", "test@example.com", "1234567890");

        // Сохраняем пользователя в базе данных
        userDao.saveUser(user);

        // Извлекаем пользователя по имени пользователя
        Optional<User> retrievedUser = userDao.getUserByUsername("testuser");

        // Проверяем, что пользователь был найден
        assertTrue(retrievedUser.isPresent(), "Пользователь не найден в базе данных");

        // Проверяем, что данные пользователя совпадают
        User actualUser = retrievedUser.get();
        assertEquals("testuser", actualUser.getUsername(), "Имя пользователя не совпадает");
        assertEquals("hashedPassword", actualUser.getPassword(), "Пароль не совпадает");
        assertEquals("test@example.com", actualUser.getEmail(), "Email не совпадает");
        assertEquals("1234567890", actualUser.getPhoneNumber(), "Номер телефона не совпадает");
    }

    @Test
    public void testGetUserByNonExistentUsername() {
        // Пытаемся получить пользователя, которого нет в базе данных
        Optional<User> retrievedUser = userDao.getUserByUsername("nonexistent");

        // Проверяем, что пользователь не найден
        assertFalse(retrievedUser.isPresent(), "Найден несуществующий пользователь");
    }

    @Test
    public void testSaveUserWithDuplicateUsername() {
        // Создаём и сохраняем первого пользователя
        User user1 = new User(0, "duplicateUser", "password1", "user1@example.com", "1111111111");
        userDao.saveUser(user1);

        // Создаём второго пользователя с тем же именем пользователя
        User user2 = new User(0, "duplicateUser", "password2", "user2@example.com", "2222222222");

        // Пытаемся сохранить второго пользователя и ожидаем исключение или логирование ошибки
        // В данном случае, метод saveUser не выбрасывает исключение, а только логирует ошибку
        // Поэтому мы проверим, что второй пользователь не был сохранён
        userDao.saveUser(user2);

        // Извлекаем пользователя по имени пользователя
        Optional<User> retrievedUser = userDao.getUserByUsername("duplicateUser");

        // Проверяем, что пользователь существует и соответствует первому пользователю
        assertTrue(retrievedUser.isPresent(), "Пользователь с дублирующим именем не найден");
        User actualUser = retrievedUser.get();
        assertEquals("user1@example.com", actualUser.getEmail(), "Email пользователя должен соответствовать первому сохранённому пользователю");
    }
}

