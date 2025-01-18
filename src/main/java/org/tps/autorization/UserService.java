package org.tps.autorization;

import lombok.Getter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Getter
public class UserService {
    private AuthService authService;
    private JSONParser parser;

    public UserService(AuthService authService, JSONParser parser) {
        this.authService = authService;
        this.parser = parser;
    }

    public boolean authenticateUser(String body) throws ParseException {
        // Например, используя JSONParser из json-simple
        JSONObject jsonObject = (JSONObject) parser.parse(body);
        String username = (String) jsonObject.get("username");
        String password = (String) jsonObject.get("password");
        return authService.authenticate(username, password);
    }
}
