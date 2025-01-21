package org.tps.personal.controller;

import com.google.gson.Gson;
import org.tps.personal.model.User;
import org.tps.personal.service.UsersService;
import static spark.Spark.*;

public class UsersController {
    private UsersService usersService = new UsersService();
    private Gson gson = new Gson();

    public void init() {
        // Получить данные пользователя по ID (JSON)
        get("/user/:id", (req, res) -> {
            String userId = req.params(":id");
            User user = usersService.getUser(userId);
            if (user != null) {
                return gson.toJson(user);
            } else {
                res.status(404);
                return "User not found";
            }
        });

        // Отобразить страницу профиля друга (HTML)
        get("/user/:id/profile", (req, res) -> {
            res.type("text/html");
            return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Friend Profile</title>\n" +
                "    <link rel=\"stylesheet\" href=\"/css/style.css\">\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>Friend Profile</h1>\n" +
                "        <div class=\"profile-info\" id=\"profile\">\n" +
                "            <p><strong>Name:</strong> <span id=\"name\">Loading...</span></p>\n" +
                "            <p><strong>Email:</strong> <span id=\"email\">Loading...</span></p>\n" +
                "        </div>\n" +
                "        <div class=\"nav\">\n" +
                "            <a href=\"/friends.html\" class=\"button\">Back to Friends</a>\n" +
                "            <a href=\"/index.html\" class=\"button\">Back to Home</a>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "\n" +
                "    <script>\n" +
                "        const friendId = window.location.pathname.split('/')[2];\n" +
                "        fetch(`/user/${friendId}`)\n" +
                "            .then(response => response.json())\n" +
                "            .then(data => {\n" +
                "                document.getElementById('name').textContent = data.name;\n" +
                "                document.getElementById('email').textContent = data.email;\n" +
                "            })\n" +
                "            .catch(error => {\n" +
                "                document.getElementById('profile').innerHTML = '<p>Error loading friend profile.</p>';\n" +
                "            });\n" +
                "    </script>\n" +
                "</body>\n" +
                "</html>";
        });
    }
}