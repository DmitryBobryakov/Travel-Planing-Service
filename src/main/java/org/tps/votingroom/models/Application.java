package org.tps.votingroom.models;

import lombok.Data;
import org.tps.votingroom.controllers.Controller;

import java.util.List;

@Data
public class Application {

  private final List<Controller> controllers;

  public void start() {
    for (Controller controller : controllers) {
      controller.initializeEndpoints();
    }
  }
}
