package org.tps.votingroom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.flywaydb.core.Flyway;
import org.tps.votingroom.controllers.VotingRoomController;
import org.tps.votingroom.controllers.VotingRoomFreemarkerController;
import org.tps.votingroom.html.TemplateFactory;
import org.tps.votingroom.models.Application;
import org.tps.votingroom.services.DataBaseService;
import spark.Service;

import java.util.List;

public class Main {
  public static void main(String[] args) {

    Config config = ConfigFactory.load();

    Flyway flyway =
        Flyway.configure()
            .locations("classpath:db/migrations/v1")
            .dataSource(config.getString("app.database.url"), config.getString("app.database.user"),
                config.getString("app.database.password"))
            .load();
    flyway.migrate();

    Service service = Service.ignite();
    DataBaseService dataBaseService = new DataBaseService(new DataSource());
    ObjectMapper objectMapper = new ObjectMapper();

    Application application = new Application(
        List.of(
            new VotingRoomController(
                service,
                dataBaseService,
                objectMapper
            ),
            new VotingRoomFreemarkerController(
                service,
                dataBaseService,
                objectMapper,
                TemplateFactory.freeMarkerEngine()
            )
        )
    );
    application.start();
  }
}
