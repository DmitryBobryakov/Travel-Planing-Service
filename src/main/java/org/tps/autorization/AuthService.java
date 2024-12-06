package org.tps.autorization;

import org.mindrot.jbcrypt.BCrypt;

public class AuthService {
    private UserDao userDao;

    public AuthService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean authenticate(String username, String password) {
        User user = userDao.getUserByUsername(username);
        if (user != null) {
            // Сравнение хэшированного пароля
            return BCrypt.checkpw(password, user.getPassword());
        }
        return false;
    }

    public void register(String username, String password, String email, String phone) {
        // Хэширование пароля перед сохранением
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        // Новый пользователь
        User newUser = new User(0, username, hashedPassword, email, phone);
        userDao.saveUser(newUser);
    }
}