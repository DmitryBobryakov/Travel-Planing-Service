package org.tps.mainpage.controllers;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.AllArgsConstructor;
import org.tps.Controller;
import spark.Service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class MainPageFreeMarkerController implements Controller {

  private final Configuration freeMarkerConfig;
  private Service service;

  @Override
  public void initializeEndpoints() {
    getMainInfo();
  }

  private void getMainInfo() {
    service.get("/", (req, res) -> {
      Template template = freeMarkerConfig.getTemplate("MainPage.ftl");
      Map<String, Object> model = new HashMap<>();
      model.put("title", "Сервис планирования совместного путешествия");

      try (StringWriter writer = new StringWriter()) {
        template.process(model, writer);
        return writer.toString();
      }
    });
  }

}
