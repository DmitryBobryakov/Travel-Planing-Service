package org.tps.autorization;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;


public class AuthServer {
    public static void main(String[] args) {
        // Настройка порта
        port(8080);

        // Настройка FreeMarker
        Configuration freeMarker = new Configuration(Configuration.VERSION_2_3_31);
        freeMarker.setClassForTemplateLoading(AuthServer.class, "/templates");
        freeMarker.setDefaultEncoding("UTF-8");
        freeMarker.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // Маршруты
        get("/login", (req, res) -> {
            res.type("text/html");
            return freeMarker.getTemplate("login.ftl");
        });

        get("/register", (req, res) -> {
            res.type("text/html");
            return freeMarker.getTemplate("register.ftl");
        });

        post("/login", (req, res) -> {
            String phone = req.queryParams("phone");
            String password = req.queryParams("password");

            // Логика аутентификации
            boolean authenticated = true;
            if (authenticated) {
                res.redirect("/success");
            } else {
                res.redirect("/login?error=true");
            }
            return null;
        });

        post("/register", (req, res) -> {
            String username = req.queryParams("username");
            String phone = req.queryParams("phone");
            String email = req.queryParams("email");
            String password = req.queryParams("password");

            // Логика регистрации
            boolean registered = true;
            if (registered) {
                res.redirect("/success");
            } else {
                res.redirect("/register?error=true");
            }
            return null;
        });

        get("/success", (req, res) -> {
            res.type("text/html");
            return "<h1>Успех!</h1><a href='/login'>Назад к входу</a>";
        });
    }
}
