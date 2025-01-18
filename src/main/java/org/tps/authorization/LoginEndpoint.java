package org.tps.authorization;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import spark.Spark;

@Slf4j
@AllArgsConstructor
public class LoginEndpoint {
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
        Spark.post("/login", (req, res) -> {
            res.type(APPLICATION_JSON);
            JSONObject responseObject = new JSONObject();

            try {
                String phone = req.queryParams("phone");
                String password = req.queryParams("password");

                boolean authenticated = userService.authenticateUser(phone, password);
                if (authenticated) {
                    res.status(200);
                    responseObject.put(STATUS, SUCCESS);
                    responseObject.put(MESSAGE, LOGIN_SUCCESS_MESSAGE);
                } else {
                    res.status(401);
                    responseObject.put(STATUS, ERROR);
                    responseObject.put(MESSAGE, INVALID_CREDENTIALS_MESSAGE);
                }
            } catch (Exception e) {
                log.error("Ошибка при обработке запроса на вход", e);
                res.status(400);
                responseObject.put(STATUS, ERROR);
                responseObject.put(MESSAGE, INVALID_REQUEST_FORMAT_MESSAGE);
            }

            return responseObject.toJSONString();
        });
    }
}
