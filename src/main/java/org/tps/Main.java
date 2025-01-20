package org.tps;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.JSONParser;
import org.tps.authorization.*;
import org.tps.authorization.controllers.AuthController;
import org.tps.authorization.controllers.AuthFreeMarkerController;
import org.tps.mainpage.controllers.MainPageFreeMarkerController;
import spark.Service;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
public class Main {

    public static void main(String[] args) {
        Service service = Service.ignite();

        // Настройка FreeMarker
        Configuration freeMarkerConfig = new Configuration(Configuration.VERSION_2_3_31);
        freeMarkerConfig.setClassForTemplateLoading(Main.class, "/templates");
        freeMarkerConfig.setDefaultEncoding("UTF-8");
        freeMarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // Подготовка сервисов и DAO
        DataSource dataSource = DatabaseConfig.getDataSource();
        UserDao userDao = new UserDao(dataSource);
        AuthService authService = new AuthService(userDao);
        JSONParser parser = new JSONParser();
        UserService userService = new UserService(authService, parser);

    Application application =
        new Application(
            List.of(
                new AuthController(userService, service),
                new AuthFreeMarkerController(freeMarkerConfig, service),
                new MainPageFreeMarkerController(freeMarkerConfig, service)));

        application.start();
    }
}
