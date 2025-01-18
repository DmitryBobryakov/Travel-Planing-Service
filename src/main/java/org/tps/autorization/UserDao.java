package org.tps.autorization;

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
    private static final String SAVE_USER_SQL = "INSERT INTO user_data (username, password, email, phoneNumber) VALUES (?, ?, ?, ?)";

    private DataSource dataSource;

    public Optional<User> getUserByUsername(String username) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_USERNAME_SQL)) {

            // Устанавливаем параметр перед выполнением запроса
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User(
                            resultSet.getInt("id"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getString("email"),
                            resultSet.getString("phoneNumber")
                    );
                    return Optional.of(user);
                }
            }
        }  catch (SQLException e) {
            log.error("Ошибка получения пользователя по имени: " + username, e);
        }
        return Optional.empty();
    }

    public void saveUser(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_USER_SQL)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhoneNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Ошибка сохранения пользователя: {}", user.getUsername(), e);
        }
    }
}
