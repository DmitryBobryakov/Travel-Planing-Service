package org.tps.autorization;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
    private static final String INVALID_REQUEST_FORMAT_MESSAGE = "Неверный формат запроса";

    private UserService userService;
    private JSONParser parser;

    public void setupRegisterEndpoint() {
        Spark.post("singup", (req, res) -> {
            res.type(APPLICATION_JSON);
            JSONObject responseObject = new JSONObject();

            try {
                boolean authenticated = userService.authenticateUser(req.body());
                if (authenticated) {
                    res.status(201);
                    responseObject.put(STATUS, SUCCESS);
                    responseObject.put(MESSAGE, REGISTER_SUCCESS_MESSAGE);
                } else {
                    log.error("Попытка регистрации с уже существующим именем пользователя");
                    res.status(409); // Conflict
                    responseObject.put(STATUS, ERROR);
                    responseObject.put(MESSAGE, USERNAME_EXISTS_MESSAGE);
                }
            } catch (ParseException e) {
                log.error("Ошибка парсинга JSON при регистрации", e);
                res.status(400);
                responseObject.put(STATUS, ERROR);
                responseObject.put(MESSAGE, INVALID_REQUEST_FORMAT_MESSAGE);
            } catch (Exception e) {
                log.error("Неизвестная ошибка при регистрации пользователя", e);
                res.status(500);
                responseObject.put(STATUS, ERROR);
                responseObject.put(MESSAGE, "Внутренняя ошибка сервера");
            }

            return responseObject.toJSONString();
        });
    }
}


