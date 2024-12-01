package org.tps.votingroom.DataBase;

import org.tps.votingroom.ConnectionService;
import org.tps.votingroom.Exceptions.AlreadyExistingException;


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

  private final ConnectionService connectionService;

  public DataBaseService(ConnectionService connectionService) {
    this.connectionService = connectionService;
  }

  public LineInformationDataBase getLinePostgres(Integer votingRoomId) throws SQLException {

    Connection connection = connectionService.getConnection();

    PreparedStatement statement = null;
    ResultSet resultSet;

    int ownerId = 0;
    String name = "";
    List<Integer> participantsId = new ArrayList<>();
    List<String> variantsNames = new ArrayList<>();
    List<Integer> variantsInterestRate = new ArrayList<>();
    int state = 0;

    try {
      statement = connection.prepareStatement("select * from votingrooms where room_id = " + votingRoomId);
      resultSet = statement.executeQuery();
      while (resultSet.next()) {
        ownerId = resultSet.getInt("owner_id");
        name = resultSet.getString("name");
        participantsId = Arrays.asList((Integer[]) resultSet.getArray("participants_id").getArray());
        variantsNames = Arrays.asList((String[]) resultSet.getArray("variants_names").getArray());
        variantsInterestRate = Arrays.asList((Integer[]) resultSet.getArray("variants_interest_rate").getArray());
        state = resultSet.getInt("state");
      }
    } catch (SQLException e) {
      throw new SQLException(e);
    } finally {
      assert statement != null;
      statement.close();
    }

    return new LineInformationDataBase(ownerId, name, participantsId, variantsNames, variantsInterestRate, state);
  }

  public void updateInterestRate(Integer votingRoomId, Integer previousValue, Integer position) throws SQLException {

    Connection connection = connectionService.getConnection();

    PreparedStatement statement = null;

    try {
      statement = connection.prepareStatement("UPDATE votingrooms SET variants_interest_rate[" + position + "] = " + (previousValue + 1) + " WHERE room_id = " + votingRoomId);
    } catch (SQLException e) {
      throw new SQLException(e);
    } finally {
      assert statement != null;
      statement.close();
      statement.executeUpdate();
    }
  }

  public Map<Integer, String[]> getFriendsList(Integer userId) throws SQLException {

    Connection connection = connectionService.getConnection();

    PreparedStatement statement = null;
    ResultSet resultSet;

    Map<Integer, String[]> friendsList = new HashMap<>();

    try {
      statement = connection.prepareStatement("select * from users where profile_id = " + userId);
      resultSet = statement.executeQuery();
      List<Integer> friendsId = new ArrayList<>();

      while (resultSet.next()) {
        friendsId = Arrays.asList((Integer[]) resultSet.getArray("friends_id").getArray());
      }

      for (Integer friendId : friendsId) {
        PreparedStatement tempStatement = null;
        ResultSet tempResultSet;

        try {
          tempStatement = connection.prepareStatement("select * from users where profile_id = " + friendId);
          tempResultSet = tempStatement.executeQuery();
          String tempFirstName = "";
          String tempLastName = "";
          String eMail = "";

          while (tempResultSet.next()) {
            tempFirstName = tempResultSet.getString("firstname");
            tempLastName = tempResultSet.getString("lastname");
            eMail = tempResultSet.getString("email");
          }

          friendsList.put(friendId, new String[]{tempFirstName, tempLastName, eMail});
        } catch (SQLException e) {
          throw new RuntimeException(e);
        } finally {
          assert tempStatement != null;
          tempStatement.close();
        }
      }
    } catch (SQLException e) {
      throw new SQLException(e);
    } finally {
      assert statement != null;
      statement.close();
    }

    return friendsList;
  }

  public void appendFriendToRoom(Integer friendId, Integer roomId) throws SQLException, AlreadyExistingException {

    Connection connection = connectionService.getConnection();

    PreparedStatement statement = null;
    ResultSet resultSet;

    try {
      statement = connection.prepareStatement("select * from votingrooms where room_id = " + roomId);
      resultSet = statement.executeQuery();

      List<Integer> friendsId = new ArrayList<>();

      while (resultSet.next()) {
        friendsId = Arrays.asList((Integer[]) resultSet.getArray("participants_id").getArray());
      }

      List<Integer> newFriendsId = new ArrayList<>();

      for (int i = 0; i < friendsId.size(); i++) {
        if (friendsId.get(i).equals(friendId)) {
          throw new AlreadyExistingException("User with id " + friendId + " already exists");
        }
        newFriendsId.add(friendsId.get(i));
      }

      newFriendsId.add(friendId);

      statement.executeUpdate("UPDATE votingrooms SET participants_id = ARRAY" + newFriendsId.toString() + " WHERE room_id = " + roomId);
    } catch (SQLException e) {
      throw new SQLException(e);
    } finally {
      assert statement != null;
      statement.close();
    }
  }

  public void endVoting(Integer votingRoomId) throws SQLException {

    Connection connection = connectionService.getConnection();

    PreparedStatement statement = null;

    try {
      statement = connection.prepareStatement("UPDATE votingrooms SET state = 0 WHERE room_id = " + votingRoomId);
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new SQLException(e);
    } finally {
      assert statement != null;
      statement.close();
    }
  }
}
