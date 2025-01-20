package org.tps.authorization.controllers;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.tps.Controller;
import spark.Service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
public class AuthFreeMarkerController implements Controller {

  private final Configuration freeMarkerConfig;
  private final Service service;

  @Override
  public void initializeEndpoints() {
    login();
    register();
  }

  private void login() {
    service.get("/login", (req, res) -> {
      Template template = freeMarkerConfig.getTemplate("login.ftl");
      Map<String, Object> model = new HashMap<>();

      if (req.queryParams("error") != null) {
        model.put("error", "Неверный номер телефона или пароль");
      }

      try (StringWriter writer = new StringWriter()) {
        template.process(model, writer);
        return writer.toString();
      }
    });
  }

  private void register() {
    service.get("/register", (req, res) -> {
      Template template = freeMarkerConfig.getTemplate("register.ftl");
      Map<String, Object> model = new HashMap<>();

      if (req.queryParams("error") != null) {
        model.put("error", "Ошибка регистрации. Возможно, имя пользователя уже существует.");
      }

      try (StringWriter writer = new StringWriter()) {
        template.process(model, writer);
        return writer.toString();
      }
    });
  }

}
