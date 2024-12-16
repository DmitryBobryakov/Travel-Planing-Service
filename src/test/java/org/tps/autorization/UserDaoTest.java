package org.tps.autorization;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.h2.jdbcx.JdbcDataSource;
import java.util.Optional;

class UserDaoTest {
    private static final String VALID_USERNAME = "testuser";
    private static final String PASSWORD = "1234";
    private static final String EMAIL = "test@example.com";
    private static final String PHONE = "998905304433";
    private static final String DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String SQL_REQUEST = "CREATE TABLE user_data (id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(255), password VARCHAR(255), email VARCHAR(255), phone VARCHAR(255))";

    private UserDao userDao;

    @BeforeEach
    public void setup() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(DB_URL);
        dataSource.setUser("sa");
        dataSource.setPassword("");

        userDao = new UserDao(dataSource);

        // Initialize schema
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(SQL_REQUEST);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSaveUser() {
        User user = new User(0, VALID_USERNAME, PASSWORD, EMAIL, PHONE);
        userDao.saveUser(user);

        Optional<User> retrievedUser = userDao.getUserByUsername(VALID_USERNAME);

        Assertions.assertTrue(retrievedUser.isPresent());
        Assertions.assertEquals(VALID_USERNAME, retrievedUser.get().getUsername());
    }
}
