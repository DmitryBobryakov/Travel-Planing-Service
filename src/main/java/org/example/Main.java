package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Controllers.VotingRoomController;
import spark.Service;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    Service service = Service.ignite();
    ObjectMapper objectMapper = new ObjectMapper();

    Application application = new Application(
        List.of(
            new VotingRoomController(
                service,
                objectMapper
            )
        )
    );
    application.start();
  }
}