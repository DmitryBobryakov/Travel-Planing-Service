package org.tps.authorization;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import spark.Spark;

import javax.sql.DataSource;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AuthController {

    public static void main(String[] args) {
        Spark.port(8080);
        log.info("Сервер запущен на порту 8080");

        // Настройка FreeMarker
        Configuration freeMarkerConfig = new Configuration(Configuration.VERSION_2_3_31);
        freeMarkerConfig.setClassForTemplateLoading(AuthController.class, "/templates");
        freeMarkerConfig.setDefaultEncoding("UTF-8");
        freeMarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // Подготовка сервисов и DAO
        DataSource dataSource = DatabaseConfig.getDataSource();
        UserDao userDao = new UserDao(dataSource);
        AuthService authService = new AuthService(userDao);
        JSONParser parser = new JSONParser();
        UserService userService = new UserService(authService, parser);

        // Настройка эндпоинтов JSON (логин / регистрация)
        LoginEndpoint loginEndpoint = new LoginEndpoint(userService);
        RegisterEndpoint registerEndpoint = new RegisterEndpoint(userService);

        // Рендеринг страниц (GET-маршруты)
        Spark.get("/login", (req, res) -> {
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

        Spark.get("/register", (req, res) -> {
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

        Spark.get("/success", (req, res) -> {
            Template template = freeMarkerConfig.getTemplate("success.ftl");
            try (StringWriter writer = new StringWriter()) {
                template.process(null, writer);
                return writer.toString();
            }
        });

        // Подключаем маршруты REST (POST /login, /register)
        loginEndpoint.setupLoginEndpoint();
        registerEndpoint.setupRegisterEndpoint();

        // Маршрут для проверки статуса (GET /status)
        Spark.get("/status", (req, res) -> {
            res.type("application/json");
            JSONObject response = new JSONObject();
            response.put("status", "running");
            return response.toJSONString();
        });

        log.info("Маршруты настроены и сервер готов к работе");
    }
}
