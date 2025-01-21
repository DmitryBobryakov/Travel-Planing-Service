package org.tps.votingroom.services;

import org.tps.votingroom.DataSource;
import org.tps.votingroom.exceptions.AlreadyExistingException;
import org.tps.votingroom.models.User;
import org.tps.votingroom.models.VotingRoomInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.tps.votingroom.models.SqlQuery.*;

public class DataBaseService {

  private final DataSource dataSource;

  public DataBaseService(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public User getUserInfo(Integer userId) throws SQLException {
    User user = new User(null, null, null, null);

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(
             "SELECT * FROM \"user\" WHERE id = " + userId
         )) {
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          user.setId(resultSet.getInt("id"));
          user.setFirstName(resultSet.getString("firstname"));
          user.setLastName(resultSet.getString("lastname"));
          user.setEmail(resultSet.getString("email"));
        }
      }

      return user;
    }
  }

  public VotingRoomInfo getVotingRoomInfo(Integer votingRoomId) throws SQLException {
    VotingRoomInfo votingRoomInfo = new VotingRoomInfo(null,
        null, null, null, null, null);

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(
             SELECT_INFORMATION_FROM_VOTINGROOM.getQuery()
         )) {
      preparedStatement.setInt(1, votingRoomId);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          votingRoomInfo.setOwnerId(resultSet.getInt("owner_id"));
          votingRoomInfo.setName(resultSet.getString("name"));
          votingRoomInfo.setParticipantsId(Arrays.asList((Integer[]) resultSet.getArray("participants_id").getArray()));
          votingRoomInfo.setVariantsNames(Arrays.asList((String[]) resultSet.getArray("variants_names").getArray()));
          votingRoomInfo.setVariantsInterestRate(Arrays.asList((Integer[]) resultSet.getArray("variants_interest_rate").getArray()));
          votingRoomInfo.setState(resultSet.getInt("state"));
        }
      }
    }

    return votingRoomInfo;
  }

  public void updateInterestRate(Integer votingRoomId, Integer previousValue, Integer position) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(
             UPDATE_INTEREST_RATE.getQuery())) {
      preparedStatement.setInt(1, position);
      preparedStatement.setInt(2, (previousValue + 1));
      preparedStatement.setInt(3, votingRoomId);
      preparedStatement.executeUpdate();
    }
  }

  public List<User> getFriendsList(Integer userId) throws SQLException {
    List<User> friendsList = new ArrayList<>();

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(
             SELECT_INFORMATION_FROM_USER.getQuery()
         )
    ) {

      preparedStatement.setInt(1, userId);

      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        List<Integer> friendsIds = new ArrayList<>();

        while (resultSet.next()) {
          friendsIds = Arrays.asList((Integer[]) resultSet.getArray("friends_id").getArray());
        }

        for (Integer id : friendsIds) {
          try (Connection newConnection = dataSource.getConnection();
               PreparedStatement newPreparedStatement = newConnection.prepareStatement(
                   SELECT_INFORMATION_FROM_USER.getQuery())
          ) {

            newPreparedStatement.setInt(1, id);

            try (ResultSet secondResultSet = newPreparedStatement.executeQuery()) {
              while (secondResultSet.next()) {
                User tempUser = new User(
                    id,
                    secondResultSet.getString("firstname"),
                    secondResultSet.getString("lastname"),
                    secondResultSet.getString("email")
                );

                friendsList.add(tempUser);
              }
            }
          }
        }
      }
    }

    return friendsList;
  }

  public void appendFriendToRoom(Integer friendId, Integer roomId) throws SQLException, AlreadyExistingException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(
             SELECT_INFORMATION_FROM_VOTINGROOM.getQuery())) {

      preparedStatement.setInt(1, roomId);

      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        List<Integer> friendsIds = new ArrayList<>();

        while (resultSet.next()) {
          friendsIds = Arrays.asList((Integer[]) resultSet.getArray("participants_id").getArray());
        }

        if (friendsIds.stream().anyMatch(id -> id.equals(friendId))) {
          throw new AlreadyExistingException("User with id " + friendId + " already exists");
        }

        List<Integer> newFriendsIds = new ArrayList<>();
        for (Integer id : friendsIds) {
          newFriendsIds.add(id);
        }
        newFriendsIds.add(friendId);
        Array sqlArray = connection.createArrayOf("integer", newFriendsIds.toArray());

        try (Connection newConnection = dataSource.getConnection();
             PreparedStatement newPreparedStatement = newConnection.prepareStatement(
                 UPDATE_PARTICIPANTS_ID.getQuery())) {
          newPreparedStatement.setArray(1, sqlArray);
          newPreparedStatement.setInt(2, roomId);
          newPreparedStatement.executeUpdate();
        }
      }
    }
  }

  public void endVoting(Integer votingRoomId) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(
             UPDATE_STATE.getQuery())) {
      preparedStatement.setInt(1, votingRoomId);
      preparedStatement.executeUpdate();
    }
  }
}
