package org.tps.autorization;

import spark.Spark;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;

@Slf4j
public class AuthController {
    public static void main(String[] args) {
        // Настройка сервера
        Spark.port(8080);
        log.info("Сервер запущен на порту 8080");

        DataSource dataSource = DatabaseConfig.getDataSource();
        UserDao userDao = new UserDao(dataSource);
        AuthService authService = new AuthService(userDao);
        JSONParser parser = new JSONParser();
        UserService userService = new UserService(authService, parser);

        LoginEndpoint loginEndpoint = new LoginEndpoint(userService);
        RegisterEndpoint registerEndpoint = new RegisterEndpoint(userService, parser);

        loginEndpoint.setupLoginEndpoint();
        registerEndpoint.setupRegisterEndpoint();

        // route для проверки статуса
        Spark.get("/status", (req, res) -> {
            res.type("application/json");
            JSONObject response = new JSONObject();
            response.put("status", "running");
            return response.toJSONString();
        });

        log.info("Маршруты настроены и сервер готов к работе");
    }
}



