package org.tps.authorization;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class AuthService {
    private final UserDao userDao;

    public AuthService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Аутентификация по телефону и паролю.
     */
    public boolean authenticate(String phone, String password) {
        Optional<User> userOpt = userDao.getUserByPhoneNumber(phone);
        return userOpt
                .filter(user -> BCrypt.checkpw(password, user.getPassword()))
                .isPresent();
    }

    /**
     * Регистрация нового пользователя.
     * Пароль хэшируется перед сохранением.
     */
    public void register(String username, String password, String email, String phoneNumber) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User newUser = new User(0, username, hashedPassword, email, phoneNumber);
        userDao.saveUser(newUser);
    }

    /**
     * Проверяем, существует ли уже пользователь с таким username.
     */
    public boolean isUsernameExists(String username) {
        return userDao.getUserByUsername(username).isPresent();
    }
}
