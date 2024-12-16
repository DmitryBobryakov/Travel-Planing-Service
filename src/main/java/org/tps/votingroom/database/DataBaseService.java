package org.tps.votingroom.database;

import org.tps.votingroom.DataSource;
import org.tps.votingroom.exceptions.AlreadyExistingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DataBaseService {

  private final DataSource dataSource;

  public DataBaseService(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public VotingRoomInf getVotingRoomInfo(Integer votingRoomId) throws SQLException {
    Integer ownerId = null;
    String name = null;
    List<Integer> participants = new ArrayList<>();
    List<String> variantNames = new ArrayList<>();
    List<Integer> variantsInterestRate = new ArrayList<>();
    Integer state = null;

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(
             "SELECT * FROM votingroom WHERE room_id = " + votingRoomId
         );
         ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        ownerId = resultSet.getInt("owner_id");
        name = resultSet.getString("name");
        participants = Arrays.asList((Integer[]) resultSet.getArray("participants_id").getArray());
        variantNames = Arrays.asList((String[]) resultSet.getArray("variants_names").getArray());
        variantsInterestRate = Arrays.asList((Integer[]) resultSet.getArray("variants_interest_rate").getArray());
        state = resultSet.getInt("state");
      }
    }

    return new VotingRoomInf(
        ownerId,
        name,
        participants,
        variantNames,
        variantsInterestRate,
        state
    );
  }

  public void updateInterestRate(Integer votingRoomId, Integer previousValue, Integer position) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(
             "UPDATE votingroom SET variants_interest_rate[" + position + "] =" +
                 " " + (previousValue + 1) + " WHERE room_id = " + votingRoomId)) {
      preparedStatement.executeUpdate();
    }
  }

  public Map<Integer, List<String>> getFriendsList(Integer userId) throws SQLException {
    Map<Integer, List<String>> friendsList = new HashMap<>();

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(
             "SELECT * FROM \"user\" WHERE profile_id = " + userId);
         ResultSet resultSet = preparedStatement.executeQuery()
    ) {
      List<Integer> friendsIds = new ArrayList<>();

      while (resultSet.next()) {
        friendsIds = Arrays.asList((Integer[]) resultSet.getArray("friends_id").getArray());
      }

      for (Integer id : friendsIds) {
        try (Connection newConnection = dataSource.getConnection();
             PreparedStatement preparedStatement1 = newConnection.prepareStatement(
                 "SELECT * FROM \"user\" WHERE profile_id = " + id);
             ResultSet resultSet1 = preparedStatement1.executeQuery()) {
          while (resultSet1.next()) {
            String tempFirstName = resultSet1.getString("firstname");
            String tempLastName = resultSet1.getString("lastname");
            String eMail = resultSet1.getString("email");

            List<String> out = new ArrayList<>();
            out.add(tempFirstName);
            out.add(tempLastName);
            out.add(eMail);

            friendsList.put(id, out);
          }
        }
      }
    }

    return friendsList;
  }

  public void appendFriendToRoom(Integer friendId, Integer roomId) throws SQLException, AlreadyExistingException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(
             "SELECT * FROM votingroom WHERE room_id = " + roomId);
         ResultSet resultSet = preparedStatement.executeQuery()) {

      List<Integer> friendsIds = new ArrayList<>();

      while (resultSet.next()) {
        friendsIds = Arrays.asList((Integer[]) resultSet.getArray("participants_id").getArray());
      }

      List<Integer> newFriendsIds = new ArrayList<>();

      for (int i = 0; i < friendsIds.size(); i++) {
        if (friendsIds.get(i).equals(friendId)) {
          throw new AlreadyExistingException("User with id " + friendId + " already exists");
        }
        newFriendsIds.add(friendsIds.get(i));
      }

      newFriendsIds.add(friendId);

      try (Connection newConnection = dataSource.getConnection();
           PreparedStatement preparedStatement1 = newConnection.prepareStatement(
               "UPDATE votingroom SET participants_id = ARRAY" + newFriendsIds.toString() + " WHERE room_id = " + roomId);) {
        preparedStatement1.executeUpdate();
      }
    }
  }

  public void endVoting(Integer votingRoomId) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(
             "UPDATE votingroom SET state = 0 WHERE room_id = " + votingRoomId)) {
      preparedStatement.executeUpdate();
    }
  }
}
