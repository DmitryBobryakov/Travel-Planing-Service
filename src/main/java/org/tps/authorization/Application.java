package org.tps.authorization;

import lombok.AllArgsConstructor;
import org.tps.Controller;

import java.util.List;

@AllArgsConstructor
public class Application {

  private final List<Controller> controllers;

  public void start() {
    for (Controller controller : controllers) {
      controller.initializeEndpoints();
    }
  }
}
