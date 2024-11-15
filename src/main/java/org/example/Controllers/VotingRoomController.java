package org.example.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Exceptions.AlreadyExistingException;
import org.example.DataBase.LineInformationDataBase;
import org.example.Requests.EndVoteRequest;
import org.example.Requests.FindFriendsRequest;
import org.example.Requests.VoteRequest;
import org.example.Responses.AppendingToRoomResponse;
import org.example.DataBase.WorkWithDataBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Service;

import java.sql.*;

public class VotingRoomController implements Controller {
  public static final Logger LOG = LoggerFactory.getLogger(VotingRoomController.class);

  private final Service service;
  private final ObjectMapper objectMapper;

  public VotingRoomController(Service service, ObjectMapper objectMapper) {
    this.service = service;
    this.objectMapper = new ObjectMapper();
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
          Integer votingRoomId = Integer.parseInt(request.params("votingRoomId"));
          LineInformationDataBase findInformationRoomResponse;

          try {
            WorkWithDataBase workWithDataBase = new WorkWithDataBase();
            findInformationRoomResponse = workWithDataBase.getLinePostgres(votingRoomId);
          } catch (SQLException e) {
            response.status(404);
            LOG.warn("Cannot find room with id={}", votingRoomId);
            return objectMapper.writeValueAsString(e.getMessage());
          }

          response.status(200);
          LOG.debug("Successfully retrieved room with id = " + votingRoomId);
          return objectMapper.writeValueAsString(findInformationRoomResponse);
        }
    );
  }

  private void vote() {
    service.post(
        "api/voting-room/:votingRoomId",
        (Request request, Response response) -> {
          response.type("application/json");
          Integer votingRoomId = Integer.parseInt(request.params("votingRoomId"));
          LineInformationDataBase informationAboutRoom;

          WorkWithDataBase workWithDataBase = new WorkWithDataBase();

          try {
            informationAboutRoom = workWithDataBase.getLinePostgres(votingRoomId);
          } catch (SQLException e) {
            response.status(404);
            LOG.warn("Cannot find room with id={}", votingRoomId);
            return objectMapper.writeValueAsString(e.getMessage());
          }

          if (informationAboutRoom.state() == 0) {
            response.status(400);
            LOG.warn("Vote is ended");
            return objectMapper.writeValueAsString("vote is ended");
          } else {
            String body = request.body();
            VoteRequest voteRequest = objectMapper.readValue(body, VoteRequest.class);

            Integer position = voteRequest.numberOfVariant();
            Integer previousValue = informationAboutRoom.variantsInterestRate()[position - 1];

            workWithDataBase.updateInterestRate(votingRoomId, previousValue, position);
            response.status(200);
            LOG.debug("Successfully vote for variant " + voteRequest.numberOfVariant() + " in room " + votingRoomId);
            return objectMapper.writeValueAsString(informationAboutRoom);
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
          FindFriendsRequest findFriendsRequest = objectMapper.readValue(body, FindFriendsRequest.class);

          WorkWithDataBase workWithDataBase = new WorkWithDataBase();

          response.status(200);
          LOG.debug("Successfully retrieved all friends");
          return objectMapper.writeValueAsString(workWithDataBase.getFriendsList(findFriendsRequest.id()));
        }
    );
  }

  private void appendFriendToRoom() {
    service.post(
        "api/voting-room/:votingRoomId/:friendId",
        (Request request, Response response) -> {
          response.type("application/json");
          Integer votingRoomId = Integer.parseInt(request.params("votingRoomId"));
          Integer friendId = Integer.parseInt(request.params("friendId"));

          WorkWithDataBase workWithDataBase = new WorkWithDataBase();
          LineInformationDataBase lineInformationDataBase = workWithDataBase.getLinePostgres(votingRoomId);

          if (lineInformationDataBase.state() == 0) {
            response.status(400);
            LOG.warn("Vote is ended");
            return objectMapper.writeValueAsString("vote is ended");
          } else {
            try {
              workWithDataBase = new WorkWithDataBase();
              workWithDataBase.appendFriendToRoom(friendId, votingRoomId);
            } catch (AlreadyExistingException e) {
              response.status(400);
              LOG.warn("User with id = " + friendId + " already exists");
              return objectMapper.writeValueAsString(e.getMessage());
            }

            response.status(200);
            LOG.debug("Successfully append friend to room " + votingRoomId);
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
          Integer votingRoomId = Integer.parseInt(request.params("votingRoomId"));
          LineInformationDataBase findInformationRoomResponse;

          try {
            WorkWithDataBase workWithDataBase = new WorkWithDataBase();
            findInformationRoomResponse = workWithDataBase.getLinePostgres(votingRoomId);
          } catch (SQLException e) {
            response.status(404);
            LOG.warn("Cannot find room with id={}", votingRoomId);
            return objectMapper.writeValueAsString(e.getMessage());
          }

          String body = request.body();
          EndVoteRequest endVoteRequest = objectMapper.readValue(body, EndVoteRequest.class);

          Integer userId = endVoteRequest.id();

          if (userId != findInformationRoomResponse.ownerId()) {
            response.status(400);
            LOG.warn("Cannot end vote");
            return objectMapper.writeValueAsString("You cannot end this vote");
          } else {
            WorkWithDataBase workWithDataBase = new WorkWithDataBase();
            workWithDataBase.endVoting(votingRoomId);
            response.status(200);
            LOG.debug("Successfully end vote");
            return objectMapper.writeValueAsString(endVoteRequest);
          }
        }
    );
  }
}
