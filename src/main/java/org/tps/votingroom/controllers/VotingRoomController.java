package org.tps.votingroom.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.tps.votingroom.database.VotingRoomInf;
import org.tps.votingroom.exceptions.AlreadyExistingException;
import org.tps.votingroom.requests.EndVoteRequest;
import org.tps.votingroom.requests.FindFriendsRequest;
import org.tps.votingroom.requests.VoteRequest;
import org.tps.votingroom.responses.AppendingToRoomResponse;
import org.tps.votingroom.database.DataBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Service;

import java.sql.SQLException;


public class VotingRoomController implements Controller {
  public static final Logger LOG = LoggerFactory.getLogger(VotingRoomController.class);

  private final Service service;
  private final DataBaseService dataBaseService;
  private final ObjectMapper objectMapper;

  public VotingRoomController(Service service, DataBaseService dataBaseService, ObjectMapper objectMapper) {
    this.service = service;
    this.dataBaseService = dataBaseService;
    this.objectMapper = objectMapper;
  }

  @Override
  public void initializeEndpoints() {
    getInformationAboutVotingRoom();
    vote();
    getAllFriends();
    appendFriendToRoom();
    endVoting();
  }

  private void getInformationAboutVotingRoom() {
    service.get(
        "api/voting-room/:votingRoomId",
        (Request request, Response response) -> {
          response.type("application/json");

          Integer votingRoomId;

          try {
            votingRoomId = Integer.parseInt(request.params("votingRoomId"));
          } catch (NumberFormatException e) {
            response.status(400);
            LOG.error("Incorrect voting room id={}", request.params("votingRoomId"));
            return objectMapper.writeValueAsString(e.getMessage());
          }

          VotingRoomInf votingRoomInf;

          try {
            votingRoomInf = dataBaseService.getVotingRoomInfo(votingRoomId);
          } catch (SQLException e) {
            response.status(404);
            LOG.error("Cannot find room with id={}", votingRoomId);
            return objectMapper.writeValueAsString(e.getMessage());
          }

          response.status(200);
          LOG.debug("Successfully retrieved room with id={}", votingRoomId);
          return objectMapper.writeValueAsString(votingRoomInf);
        }
    );
  }

  private void vote() {
    service.post(
        "api/voting-room/:votingRoomId",
        (Request request, Response response) -> {
          response.type("application/json");

          Integer votingRoomId;

          try {
            votingRoomId = Integer.parseInt(request.params("votingRoomId"));
          } catch (NumberFormatException e) {
            response.status(400);
            LOG.error("Incorrect voting room id={}", request.params("votingRoomId"));
            return objectMapper.writeValueAsString(e.getMessage());
          }

          VotingRoomInf votingRoomInf;

          try {
            votingRoomInf = dataBaseService.getVotingRoomInfo(votingRoomId);
          } catch (SQLException e) {
            response.status(404);
            LOG.error("Cannot find room with id={}", votingRoomId);
            return objectMapper.writeValueAsString(e.getMessage());
          }

          if (votingRoomInf.state() == 0) {
            response.status(409);
            LOG.error("Vote is ended");
            return objectMapper.writeValueAsString("Vote is ended");
          } else {
            String body = request.body();
            VoteRequest voteRequest = objectMapper.readValue(body, VoteRequest.class);

            Integer position = voteRequest.numberOfVariant();
            Integer previousValue = votingRoomInf.variantsInterestRate().get(position - 1);

            dataBaseService.updateInterestRate(votingRoomId, previousValue, position);
            response.status(200);
            LOG.debug("Successfully vote for variant={}" + " in room with id={}", voteRequest.numberOfVariant(), votingRoomId);
            return objectMapper.writeValueAsString(votingRoomInf);
          }
        }
    );
  }

  private void getAllFriends() {
    service.get(
        "api/voting-room/:votingRoomId/friends",
        (Request request, Response response) -> {
          response.type("application/json");
          String body = request.body();

          FindFriendsRequest findFriendsRequest;

          try {
            findFriendsRequest = objectMapper.readValue(body, FindFriendsRequest.class);
          } catch (JsonProcessingException e) {
            response.status(400);
            LOG.error("Incorrect input data format");
            return objectMapper.writeValueAsString(e.getMessage());
          }

          response.status(200);
          LOG.debug("Successfully retrieved all friends");
          return objectMapper.writeValueAsString(dataBaseService.getFriendsList(findFriendsRequest.id()));
        }
    );
  }

  private void appendFriendToRoom() {
    service.post(
        "api/voting-room/:votingRoomId/:friendId",
        (Request request, Response response) -> {
          response.type("application/json");

          Integer votingRoomId;
          Integer friendId;

          try {
            votingRoomId = Integer.parseInt(request.params("votingRoomId"));
          } catch (NumberFormatException e) {
            response.status(400);
            LOG.error("Incorrect voting room id={}", request.params("votingRoomId"));
            return objectMapper.writeValueAsString(e.getMessage());
          }

          try {
            friendId = Integer.parseInt(request.params("friendId"));
          } catch (NumberFormatException e) {
            response.status(400);
            LOG.error("Incorrect friend id={}", request.params("friendId"));
            return objectMapper.writeValueAsString(e.getMessage());
          }

          VotingRoomInf votingRoomInf;

          try {
            votingRoomInf = dataBaseService.getVotingRoomInfo(votingRoomId);
          } catch (SQLException e) {
            response.status(404);
            LOG.error("Cannot find room with id={}", votingRoomId);
            return objectMapper.writeValueAsString(e.getMessage());
          }

          if (votingRoomInf.state() == 0) {
            response.status(409);
            LOG.error("Vote is ended");
            return objectMapper.writeValueAsString("vote is ended");
          } else {
            try {
              dataBaseService.appendFriendToRoom(friendId, votingRoomId);
            } catch (AlreadyExistingException e) {
              response.status(400);
              LOG.error("User with id = {} already exists", friendId, e);
              return objectMapper.writeValueAsString(e.getMessage());
            }

            response.status(200);
            LOG.debug("Successfully append friend to room with id={}", votingRoomId);
            return objectMapper.writeValueAsString(new AppendingToRoomResponse(friendId, votingRoomId));
          }
        }
    );
  }

  private void endVoting() {
    service.put(
        "api/voting-room/:votingRoomId",
        (Request request, Response response) -> {
          response.type("application/json");

          Integer votingRoomId;

          try {
            votingRoomId = Integer.parseInt(request.params("votingRoomId"));
          } catch (NumberFormatException e) {
            response.status(400);
            LOG.error("Incorrect voting room id={}", request.params("votingRoomId"));
            return objectMapper.writeValueAsString(e.getMessage());
          }

          VotingRoomInf votingRoomInf;

          try {
            votingRoomInf = dataBaseService.getVotingRoomInfo(votingRoomId);
          } catch (SQLException e) {
            response.status(404);
            LOG.error("Cannot find room with id={}", votingRoomId);
            return objectMapper.writeValueAsString(e.getMessage());
          }

          String body = request.body();
          EndVoteRequest endVoteRequest;

          try {
            endVoteRequest = objectMapper.readValue(body, EndVoteRequest.class);
          } catch (JsonProcessingException e) {
            response.status(400);
            LOG.error("Incorrect input data format");
            return objectMapper.writeValueAsString(e.getMessage());
          }

          Integer userId = endVoteRequest.id();

          if (!userId.equals(votingRoomInf.ownerId())) {
            response.status(403);
            LOG.error("Cannot end vote");
            return objectMapper.writeValueAsString("You cannot end this vote");
          } else {
            dataBaseService.endVoting(votingRoomId);
            response.status(200);
            LOG.debug("Successfully end vote");
            return objectMapper.writeValueAsString(endVoteRequest);
          }
        }
    );
  }
}
