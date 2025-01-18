package org.tps.authorization;

import lombok.Getter;
import org.json.simple.parser.JSONParser;

@Getter
public class UserService {
    private final AuthService authService;
    private final JSONParser parser;

    public UserService(AuthService authService, JSONParser parser) {
        this.authService = authService;
        this.parser = parser;
    }

    /**
     * Аутентификация по телефону (логин).
     */
    public boolean authenticateUser(String phone, String password) {
        return authService.authenticate(phone, password);
    }

    /**
     * Регистрация нового пользователя по username/email/phone.
     * Если username уже есть, возвращаем false, иначе true.
     */
    public boolean registerUser(String username, String password, String email, String phone) {
        if (authService.isUsernameExists(username)) {
            return false;
        }
        authService.register(username, password, email, phone);
        return true;
    }
}
