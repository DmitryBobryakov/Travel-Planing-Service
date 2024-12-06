package org.tps.autorization;

import spark.Spark;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sql.DataSource;

public class AuthController {
    public static void main(String[] args) {
        DataSource dataSource = DatabaseConfig.getDataSource();
        UserDao userDao = new UserDao(dataSource);
        AuthService authService = new AuthService(userDao);
        JSONParser parser = new JSONParser();

        LoginEndpoint loginEndpoint = new LoginEndpoint(authService, parser);
        RegisterEndpoint registerEndpoint = new RegisterEndpoint(authService, parser);

        loginEndpoint.setupLoginEndpoint();
        registerEndpoint.setupRegisterEndpoint();
    }
}

class LoginEndpoint {
    private static final Logger log = LoggerFactory.getLogger(LoginEndpoint.class);
    private static final String STATUS = "status";
    private static final String MESSAGE = "message";
    private static final String SUCCESS = "SUCCESS";
    private static final String ERROR = "ERROR";
    private static final String LOGIN_SUCCESS_MESSAGE = "Вход выполнен успешно";
    private static final String INVALID_CREDENTIALS_MESSAGE = "Неверные учетные данные";
    private static final String INVALID_REQUEST_FORMAT_MESSAGE = "Неверный формат запроса";
    private static final String ERROR_PARSING_THE_REGISTRATION_REQUEST = "Ошибка при разборе запроса на регистрацию";

    private static AuthService authService;
    private static JSONParser parser;

    public LoginEndpoint(AuthService authService, JSONParser parser) {
        LoginEndpoint.authService = authService;
        LoginEndpoint.parser = parser;
    }

    public void setupLoginEndpoint() {
        // Обрабатываем HTTP POST-запрос для входа
        Spark.post("/login", (req, res) -> {
            res.type("application/json");
            JSONObject responseObject = new JSONObject();

            try {
                JSONObject requestBody = (JSONObject) parser.parse(req.body());
                String username = (String) requestBody.get("username");
                String password = (String) requestBody.get("password");

                boolean authenticated = authService.authenticate(username, password);
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
                log.error(ERROR_PARSING_THE_REGISTRATION_REQUEST, e);
                res.status(400);
                responseObject.put(STATUS, ERROR);
                responseObject.put(MESSAGE, INVALID_REQUEST_FORMAT_MESSAGE);
            }

            return responseObject.toJSONString();
        });
    }
}

class RegisterEndpoint {
    private static final Logger log = LoggerFactory.getLogger(RegisterEndpoint.class);
    private static final String STATUS = "status";
    private static final String MESSAGE = "message";
    private static final String SUCCESS = "SUCCESS";
    private static final String ERROR = "ERROR";
    private static final String REGISTER_SUCCESS_MESSAGE = "Пользователь успешно зарегистрирован";
    private static final String INVALID_REQUEST_FORMAT_MESSAGE = "Неверный формат запроса";
    private static final String ERROR_PARSING_THE_REGISTRATION_REQUEST = "Ошибка при разборе запроса на регистрацию";

    private final AuthService authService;
    private final JSONParser parser;

    public RegisterEndpoint(AuthService authService, JSONParser parser) {
        this.authService = authService;
        this.parser = parser;
    }

    public void setupRegisterEndpoint() {
    //Обрабатываем HTTP POST-запрос для регистрации
        Spark.post("/register", (req, res) -> {
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
                responseObject.put(STATUS, SUCCESS);
                responseObject.put(MESSAGE, REGISTER_SUCCESS_MESSAGE);
            } catch (ParseException e) {
                log.error(ERROR_PARSING_THE_REGISTRATION_REQUEST, e);
                res.status(400);
                responseObject.put(STATUS, ERROR);
                responseObject.put(MESSAGE, INVALID_REQUEST_FORMAT_MESSAGE);
            }

            return responseObject.toJSONString();
        });
    }
}