package org.example;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;

public class Main {
    public static void main(String[] args) throws SQLException {
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
        Configuration freeMarkerConfiguration = new Configuration();
        freeMarkerConfiguration.setTemplateLoader(new ClassTemplateLoader(Main.class, "/"));
        freeMarkerEngine.setConfiguration(freeMarkerConfiguration);

        DatabaseManager dbManager = new DatabaseManager();

        // Главная страница
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("title", "Сервис планирования совместного путешествия");
            return freeMarkerEngine.render(new ModelAndView(model, "VotingRoomText.ftl"));
        });

        // Страница голосования
        get("/startVoting", (request, response) -> {
            List<Trip> trips = dbManager.getTrips();
            Map<String, Object> model = new HashMap<>();
            model.put("title", "Начало голосования");
            model.put("trips", trips);
            return freeMarkerEngine.render(new ModelAndView(model, "VotingPage.ftl"));
        });
    }
}
