package org.tps.authorization;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class UserDao {
    private static final Logger log = LoggerFactory.getLogger(UserDao.class);
    private static final String GET_USER_BY_USERNAME_SQL = "SELECT * FROM user_data WHERE username = ?";
    private static final String GET_USER_BY_PHONE_SQL = "SELECT * FROM user_data WHERE phoneNumber = ?";
    private static final String SAVE_USER_SQL = "INSERT INTO user_data (username, password, email, phoneNumber) VALUES (?, ?, ?, ?)";

    private final DataSource dataSource;

    public Optional<User> getUserByUsername(String username) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_USERNAME_SQL))
        {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    User user = new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email"),
                            rs.getString("phoneNumber")
                    );
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            log.error("Ошибка получения пользователя по имени: " + username, e);
        }
        return Optional.empty();
    }

    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_PHONE_SQL))
        {
            statement.setString(1, phoneNumber);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    User user = new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email"),
                            rs.getString("phoneNumber")
                    );
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            log.error("Ошибка получения пользователя по номеру телефона: " + phoneNumber, e);
        }
        return Optional.empty();
    }

    public void saveUser(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_USER_SQL))
        {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhoneNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Ошибка сохранения пользователя: {}", user.getUsername(), e);
            throw new RuntimeException("Такое имя уже есть", e);
        }
    }
}
