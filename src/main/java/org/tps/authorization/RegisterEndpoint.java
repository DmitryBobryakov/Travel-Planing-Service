package org.tps.authorization;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import spark.Spark;

@Slf4j
@AllArgsConstructor
public class RegisterEndpoint {
    private static final String APPLICATION_JSON = "application/json";
    private static final String STATUS = "status";
    private static final String MESSAGE = "message";
    private static final String SUCCESS = "SUCCESS";
    private static final String ERROR = "ERROR";
    private static final String REGISTER_SUCCESS_MESSAGE = "Пользователь успешно зарегистрирован";
    private static final String USERNAME_EXISTS_MESSAGE = "Имя пользователя уже существует";

    private UserService userService;

    public void setupRegisterEndpoint() {
        Spark.post("/register", (req, res) -> {
            res.type(APPLICATION_JSON);
            JSONObject responseObject = new JSONObject();

            try {
                String phone = req.queryParams("phone");
                String username = req.queryParams("username");
                String email = req.queryParams("email");
                String password = req.queryParams("password");

                boolean registered = userService.registerUser(username, password, email, phone);
                if (registered) {
                    // Успешная регистрация
                    res.status(201);
                    responseObject.put(STATUS, SUCCESS);
                    responseObject.put(MESSAGE, REGISTER_SUCCESS_MESSAGE);
                } else {
                    // Такой username уже есть
                    res.status(409);
                    responseObject.put(STATUS, ERROR);
                    responseObject.put(MESSAGE, USERNAME_EXISTS_MESSAGE);
                }
            } catch (Exception e) {
                log.error("Ошибка при регистрации пользователя", e);
                res.status(500);
                responseObject.put(STATUS, ERROR);
                responseObject.put(MESSAGE, "Внутренняя ошибка сервера");
            }

            return responseObject.toJSONString();
        });
    }
}
