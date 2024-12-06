package org.tps.autorization;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class AuthService {
    private UserDao userDao;

    public AuthService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean authenticate(String username, String password) {
        Optional<User> user = userDao.getUserByUsername(username);
      // Сравнение хэшированного пароля
      return user.filter(value -> BCrypt.checkpw(password, value.getPassword())).isPresent();
    }

    public void register(String username, String password, String email, String phone) {
        // Хэширование пароля перед сохранением
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        // Новый пользователь
        User newUser = new User(0, username, hashedPassword, email, phone);
        userDao.saveUser(newUser);
    }
}