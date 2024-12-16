package org.tps.votingroom;

import org.tps.votingroom.controllers.Controller;

import java.util.List;

public class Application {
  private final List<Controller> controllers;

  public Application(List<Controller> controllers) {
    this.controllers = controllers;
  }

  public void start() {
    for (Controller controller : controllers) {
      controller.initializeEndpoints();
    }
  }
}
