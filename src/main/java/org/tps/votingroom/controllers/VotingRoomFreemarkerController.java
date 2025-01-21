package org.tps.votingroom.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tps.votingroom.exceptions.UserFindException;
import org.tps.votingroom.models.User;
import org.tps.votingroom.models.VotingRoomInfo;
import org.tps.votingroom.services.DataBaseService;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Service;
import spark.template.freemarker.FreeMarkerEngine;

import java.sql.SQLException;
import java.util.*;

public class VotingRoomFreemarkerController implements Controller {

  private final static Logger LOG = LoggerFactory.getLogger(VotingRoomFreemarkerController.class);

  private final Service service;
  private final DataBaseService dataBaseService;
  private final ObjectMapper objectMapper;
  private final FreeMarkerEngine freeMarkerEngine;


  public VotingRoomFreemarkerController(Service service, DataBaseService dataBaseService, ObjectMapper objectMapper, FreeMarkerEngine freeMarkerEngine) {
    this.service = service;
    this.dataBaseService = dataBaseService;
    this.objectMapper = objectMapper;
    this.freeMarkerEngine = freeMarkerEngine;
  }

  @Override
  public void initializeEndpoints() {
    getInformation();
  }

  private void getInformation() {
    service.get(
        "/voting-room/:votingRoomId",
        (Request request, Response response) -> {
          response.type("text/html; charset=utf-8");

          Integer votingRoomId = Integer.parseInt(request.params("votingRoomId"));

          VotingRoomInfo votingRoomInfo = dataBaseService.getVotingRoomInfo(votingRoomId);

          //paricipants
          List<Integer> ids = votingRoomInfo.getParticipantsId();

          List<Map<String, String>> modelIds =
              ids.stream()
                  .map(id -> {
                    try {
                      return Map.of("name", dataBaseService.getUserInfo(id).getFirstName(),
                          "surname", dataBaseService.getUserInfo(id).getLastName());
                    } catch (SQLException e) {
                      throw new UserFindException("Cannot find user with id = " + id);
                    }
                  })
                  .toList();

          //variants
          List<String> names = votingRoomInfo.getVariantsNames();
          List<Integer> interestRate = votingRoomInfo.getVariantsInterestRate();

          Integer votingNumbers = 0;
          for (int i = 0; i < interestRate.size(); i++) {
            votingNumbers += interestRate.get(i);
          }

          List<Map<String, String>> modelVariants = new ArrayList<>();
          for (int i = 0; i < names.size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("name", names.get(i));
            map.put("count", String.valueOf(interestRate.get(i)));
            map.put("interest", String.format(Locale.US, "%.2f", 100.0 * interestRate.get(i) / votingNumbers));
            map.put("number", String.valueOf((i + 1)));
            modelVariants.add(map);
          }

          //owner friends
          Integer ownerId = votingRoomInfo.getOwnerId();
          List<User> friendsIds = dataBaseService.getFriendsList(ownerId);

          List<Map<String, String>> modelFriends = friendsIds.stream()
              .map(user -> {
                return Map.of("id", String.valueOf(user.getId()),
                    "name", user.getFirstName(),
                    "lastname", user.getLastName());
              })
              .toList();

          Map<String, Object> modelMap = new HashMap<>();
          modelMap.put("ids", modelIds);
          modelMap.put("variants", modelVariants);
          modelMap.put("friends", modelFriends);

          LOG.debug("Info displayed");
          response.status(200);
          return freeMarkerEngine.render(new ModelAndView(modelMap, "index.ftl"));
        }
    );
  }
}
