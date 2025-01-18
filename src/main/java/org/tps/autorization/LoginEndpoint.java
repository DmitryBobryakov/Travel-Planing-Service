package org.tps.autorization;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import spark.Spark;

@Slf4j
@AllArgsConstructor
class LoginEndpoint {
    private static final String APPLICATION_JSON = "application/json";
    private static final String STATUS = "status";
    private static final String MESSAGE = "message";
    private static final String SUCCESS = "SUCCESS";
    private static final String ERROR = "ERROR";
    private static final String LOGIN_SUCCESS_MESSAGE = "Вход выполнен успешно";
    private static final String INVALID_CREDENTIALS_MESSAGE = "Неверные учетные данные";
    private static final String INVALID_REQUEST_FORMAT_MESSAGE = "Неверный формат запроса";

    private UserService userService;

    public void setupLoginEndpoint() {
        Spark.post("singin", (req, res) -> {
            res.type(APPLICATION_JSON);
            JSONObject responseObject = new JSONObject();

            try {
                boolean authenticated = userService.authenticateUser(req.body());
                if (authenticated) {
                    res.status(200);
                    responseObject.put(STATUS, SUCCESS);
                    responseObject.put(MESSAGE, LOGIN_SUCCESS_MESSAGE);
                } else {
                    res.status(401);
                    responseObject.put(STATUS, ERROR);
                    responseObject.put(MESSAGE, INVALID_CREDENTIALS_MESSAGE);
                }
            } catch (ParseException e) {
                log.error("Ошибка парсинга запроса для входа", e);
                res.status(400);
                responseObject.put(STATUS, ERROR);
                responseObject.put(MESSAGE, INVALID_REQUEST_FORMAT_MESSAGE);
            }

            return responseObject.toJSONString();
        });
    }
}