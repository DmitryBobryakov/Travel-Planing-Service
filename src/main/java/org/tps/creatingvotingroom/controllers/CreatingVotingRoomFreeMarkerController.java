package org.tps.creatingvotingroom.controllers;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.AllArgsConstructor;
import org.tps.Controller;
import spark.Service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class CreatingVotingRoomFreeMarkerController implements Controller {

  private final Configuration freeMarkerConfig;
  private final Service service;

  @Override
  public void initializeEndpoints() {
    getCreatingPage();
  }

  private void getCreatingPage() {service.get("/create", (req, res) -> {
      Template template = freeMarkerConfig.getTemplate("CreateVotingRoom.ftl");
      Map<String, Object> model = new HashMap<>();
      model.put("title", "Сервис планирования совместного путешествия");

      try (StringWriter writer = new StringWriter()) {
        template.process(model, writer);
        return writer.toString();
      }
    });
  }


}
