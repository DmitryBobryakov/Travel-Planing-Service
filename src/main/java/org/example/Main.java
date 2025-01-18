package org.example;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static spark.Spark.get;

public class Main {

    public static void main(String[] args) throws SQLException {

        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
        Configuration freeMarkerConfiguration = new Configuration();
        freeMarkerConfiguration.setTemplateLoader(new ClassTemplateLoader(Main.class, "/"));
        freeMarkerEngine.setConfiguration(freeMarkerConfiguration);
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("title", "Создание голосования");
            return freeMarkerEngine.render(new ModelAndView(model, "template/VotingRoomText.ftl"));
        });
    }
}
