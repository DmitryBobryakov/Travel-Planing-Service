package org.example;

import com.google.gson.*;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.sql.SQLException;
import java.util.*;

import static spark.Spark.*;

public class Main {

    private static final Gson gson = new Gson();

    public static void main(String[] args) {

        // Настройка шаблонизатора FreeMarker
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
        Configuration freeMarkerConfiguration = new Configuration(Configuration.VERSION_2_3_21);
        freeMarkerConfiguration.setTemplateLoader(new ClassTemplateLoader(Main.class, "/"));
        freeMarkerEngine.setConfiguration(freeMarkerConfiguration);

        // Отдаём HTML-страницу (шаблон) при GET запросе
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("title", "Создание голосования");
            return freeMarkerEngine.render(new ModelAndView(model, "template/VotingRoomText.ftl"));
        });

        // При клике "Создать голосование" идёт JSON POST на /AddData
        post("/AddData", (req, res) -> {
            // Парсим JSON из тела запроса
            JsonObject json = gson.fromJson(req.body(), JsonObject.class);

            String dateFinish = json.get("date_finish").getAsString();

            // В вашем случае owner_name - всегда "Sasha"
            String ownerName = "Sasha";

            // Считываем массив друзей
            JsonArray friendsArray = json.getAsJsonArray("friends_names");
            String[] friends = new String[friendsArray.size()];
            for (int i = 0; i < friendsArray.size(); i++) {
                friends[i] = friendsArray.get(i).getAsString();
            }

            // Считываем массив путешествий
            JsonArray trip_links_array = json.getAsJsonArray("trip_links");
            String[] trip_links = new String[trip_links_array.size()];
            for (int i = 0; i < trip_links_array.size(); i++) {
                trip_links[i] = trip_links_array.get(i).getAsString();
            }

            JsonArray trip_names_array = json.getAsJsonArray("trip_name");
            String[] trip_names = new String[trip_names_array.size()];
            for (int i = 0; i < trip_names_array.size(); i++) {
                trip_names[i] = trip_names_array.get(i).getAsString();
            }
            // Добавляем в БД
            try {
                AddingVotingRoom.addVoting(ownerName, dateFinish, friends, trip_links, trip_names);
            } catch (SQLException e) {
                e.printStackTrace();
                // Можно установить код ошибки и вернуть JSON
                res.status(500);
                return gson.toJson(Collections.singletonMap("error", "Не удалось создать голосование."));
            }

            // По желанию можно сделать редирект на /Success или вернуть JSON-ответ
            // Допустим делаем редирект:
            res.redirect("/Success");
            return null;
        });

        // Допустим сделаем ручку для /Success
        get("/Success", (req, res) -> "Голосование успешно создано!");
    }
}
