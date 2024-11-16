package org.example;

import static spark.Spark.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.sql.DataSource;

public class AuthController {
    public static void main(String[] args) {
        DataSource dataSource = DatabaseConfig.getDataSource();
        UserDao userDao = new UserDao(dataSource);
        AuthService authService = new AuthService(userDao);
        JSONParser parser = new JSONParser();

        //Обрабатываем HTTP POST-запрос для входа
        post("/login", (req, res) -> {
            res.type("application/json");
            JSONObject responseObject = new JSONObject();

            try {
                JSONObject requestBody = (JSONObject) parser.parse(req.body());
                String username = (String) requestBody.get("username");
                String password = (String) requestBody.get("password");

                boolean authenticated = authService.authenticate(username, password);
                if (authenticated) {
                    res.status(200);
                    responseObject.put("status", "SUCCESS");
                    responseObject.put("message", "Вход выполнен успешно");
                } else {
                    res.status(401);
                    responseObject.put("status", "ERROR");
                    responseObject.put("message", "Неверно введены данные");
                }
            } catch (ParseException e) {
                res.status(400);
                responseObject.put("status", "ERROR");
                responseObject.put("message", "Неверный формат запроса");
            }

            return
                    responseObject.toJSONString();
        });

        //Обрабатываем HTTP POST-запрос для регистрации
        post("/register", (req, res) -> {
            res.type("application/json");
            JSONObject responseObject = new JSONObject();

            try {
                JSONObject requestBody = (JSONObject) parser.parse(req.body());
                String username = (String) requestBody.get("username");
                String password = (String) requestBody.get("password");
                String email = (String) requestBody.get("email");
                String phone = (String) requestBody.get("phone");

                authService.register(username, password, email, phone);
                res.status(201);
                responseObject.put("status", "SUCCESS");
                responseObject.put("message", "User registered successfully");
            } catch (ParseException e) {
                res.status(400);
                responseObject.put("status", "ERROR");
                responseObject.put("message", "Invalid request format");
            }

            return responseObject.toJSONString();
        });
    }
}
