package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class Main {

    private static final Gson gson = new Gson();

    public static void main(String[] args) throws SQLException {

        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
        Configuration freeMarkerConfiguration = new Configuration();
        freeMarkerConfiguration.setTemplateLoader(new ClassTemplateLoader(Main.class, "/"));
        freeMarkerEngine.setConfiguration(freeMarkerConfiguration);

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("title", "Создание голосования");
            model.put("friends", new String[]{"biba", "boba"});
            return freeMarkerEngine.render(new ModelAndView(model, "template/VotingRoomText.ftl"));
        });

        // Обработка POST-запроса при нажатии на кнопку
        post("/AddData", (req, res) -> {
            res.type("application/json");
            JsonObject json = gson.fromJson(req.body(), JsonObject.class);
            String date_start =  ((char) 39) + json.get("date_start").getAsString() + ((char) 39);
            String date_finish =  ((char) 39) + json.get("date_finish").getAsString() + ((char) 39);
            String owner_name = ((char) 39)+"Sasha"+ ((char) 39); // пока нет возможности брать имя юзера, поэтому овнер - я
            String[] friends_names = { ((char) 39) + json.get("friends_names").getAsString() + ((char) 39)};
            String[] trip_variants =  { ((char) 39) + json.get("trip_variants").getAsString() + ((char) 39) };
            AddingVotingRoom.addVoting(owner_name,date_start,date_finish,friends_names,trip_variants);
            res.redirect("/Success");
            return true;
        });
    }
}
