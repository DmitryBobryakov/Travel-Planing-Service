package org.tps.authorization.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.tps.Controller;
import org.tps.authorization.UserService;
import spark.Service;

@Slf4j
@AllArgsConstructor
public class AuthController implements Controller {

  private static final String APPLICATION_JSON = "application/json";
  private static final String STATUS = "status";
  private static final String MESSAGE = "message";
  private static final String SUCCESS = "SUCCESS";
  private static final String ERROR = "ERROR";
  private static final String LOGIN_SUCCESS_MESSAGE = "Вход выполнен успешно";
  private static final String INVALID_CREDENTIALS_MESSAGE = "Неверные учетные данные";
  private static final String INVALID_REQUEST_FORMAT_MESSAGE = "Неверный формат запроса";
  private static final String REGISTER_SUCCESS_MESSAGE = "Пользователь успешно зарегистрирован";
  private static final String USERNAME_EXISTS_MESSAGE = "Имя пользователя уже существует";

  private UserService userService;
  private Service service;

  @Override
  public void initializeEndpoints() {
    setupLoginEndpoint();
    setupRegisterEndpoint();
  }

  public void setupLoginEndpoint() {
    service.post("/login", (req, res) -> {
      res.type(APPLICATION_JSON);
      JSONObject responseObject = new JSONObject();

      try {
        String phone = req.queryParams("phone");
        String password = req.queryParams("password");

        boolean authenticated = userService.authenticateUser(phone, password);
        if (authenticated) {
          res.redirect("/");
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

  public void setupRegisterEndpoint() {
    service.post("/register", (req, res) -> {
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
          res.redirect("/");
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
