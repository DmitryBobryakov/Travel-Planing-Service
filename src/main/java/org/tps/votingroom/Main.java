package org.tps.votingroom;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.tps.votingroom.Сontrollers.VotingRoomController;
import org.tps.votingroom.DataBase.DataBaseService;
import spark.Service;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    Service service = Service.ignite();
    DataBaseService dataBaseService = new DataBaseService(new ConnectionService());
    ObjectMapper objectMapper = new ObjectMapper();

    Application application = new Application(
        List.of(
            new VotingRoomController(
                service,
                dataBaseService,
                objectMapper
            )
        )
    );
    application.start();
  }
}