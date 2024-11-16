package org.example.DataBase;

import org.example.Exceptions.AlreadyExistingException;

import java.sql.*;
import java.util.*;

public class WorkWithDataBase {

  public LineInformationDataBase getLinePostgres(Integer votingRoomId) throws ClassNotFoundException, SQLException {
    Class.forName("org.postgresql.Driver");
    Connection connection =
        DriverManager.getConnection("jdbc:postgresql://localhost:5438/postgres", "postgres",
            "12345");

    Statement statement = connection.createStatement();

    String sql = "select * from votingrooms where room_id = " + votingRoomId;
    ResultSet resultSet = statement.executeQuery(sql);

    Integer ownerId = 0;
    String name = "";
    Integer[] participantsId = new Integer[0];
    String[] variantsNames = new String[0];
    Integer[] variantsInterestRate = new Integer[0];
    Integer state = 0;

    while (resultSet.next()) {
      ownerId = resultSet.getInt("owner_id");
      name = resultSet.getString("name");
      participantsId = (Integer[]) resultSet.getArray("participants_id").getArray();
      variantsNames = (String[]) resultSet.getArray("variants_names").getArray();
      variantsInterestRate = (Integer[]) resultSet.getArray("variants_interest_rate").getArray();
      state = resultSet.getInt("state");
    }

    return new LineInformationDataBase(ownerId, name, participantsId, variantsNames, variantsInterestRate, state);
  }

  public void updateInterestRate(Integer votingRoomId, Integer previousValue, Integer position) throws ClassNotFoundException, SQLException {
    Class.forName("org.postgresql.Driver");
    Connection connection =
        DriverManager.getConnection("jdbc:postgresql://localhost:5438/postgres", "postgres",
            "12345");

    Statement statement = connection.createStatement();

    statement.executeUpdate("UPDATE votingrooms SET variants_interest_rate[" + position + "] = " + (previousValue + 1) + " WHERE room_id = " + votingRoomId);
  }

  public Map<Integer, String[]> getFriendsList(Integer userId) throws ClassNotFoundException, SQLException {
    Class.forName("org.postgresql.Driver");
    Connection connection =
        DriverManager.getConnection("jdbc:postgresql://localhost:5438/postgres", "postgres",
            "12345");

    Statement statement = connection.createStatement();

    String sql = "select * from users where profile_id = " + userId;
    ResultSet resultSet = statement.executeQuery(sql);

    Integer[] friendsId = new Integer[0];

    while (resultSet.next()) {
      friendsId = (Integer[]) resultSet.getArray("friends_id").getArray();
    }

    Map<Integer, String[]> friendsList = new HashMap<>();

    for (Integer friendId : friendsId) {
      String sqlTemp = "select * from users where profile_id = " + friendId;
      ResultSet resultSetTemp = statement.executeQuery(sqlTemp);

      String tempFirstName = "";
      String tempLastName = "";
      String eMail = "";

      while (resultSetTemp.next()) {
        tempFirstName = resultSetTemp.getString("firstname");
        tempLastName = resultSetTemp.getString("lastname");
        eMail = resultSetTemp.getString("email");
      }

      friendsList.put(friendId, new String[]{tempFirstName, tempLastName, eMail});
    }

    return friendsList;
  }

  public void appendFriendToRoom(Integer friendId, Integer roomId) throws ClassNotFoundException, SQLException {
    Class.forName("org.postgresql.Driver");
    Connection connection =
        DriverManager.getConnection("jdbc:postgresql://localhost:5438/postgres", "postgres",
            "12345");

    Statement statement = connection.createStatement();

    String sql = "select * from votingrooms where room_id = " + roomId;
    ResultSet resultSet = statement.executeQuery(sql);

    Integer[] friendsId = new Integer[0];

    while (resultSet.next()) {
      friendsId = (Integer[]) resultSet.getArray("participants_id").getArray();
    }

    Integer[] newFriendsId = new Integer[friendsId.length + 1];

    for (int i = 0; i < friendsId.length; i++) {
      if (friendsId[i] == friendId) {
        throw new AlreadyExistingException("User with id " + friendId + " already exists");
      }
      newFriendsId[i] = friendsId[i];
    }

    newFriendsId[newFriendsId.length - 1] = friendId;

    statement.executeUpdate("UPDATE votingrooms SET participants_id = ARRAY" + Arrays.toString(newFriendsId) + " WHERE room_id = " + roomId);
  }

  public void endVoting(Integer votingRoomId) throws ClassNotFoundException, SQLException {
    Class.forName("org.postgresql.Driver");
    Connection connection =
        DriverManager.getConnection("jdbc:postgresql://localhost:5438/postgres", "postgres",
            "12345");

    Statement statement = connection.createStatement();

    statement.executeUpdate("UPDATE votingrooms SET state = 0 WHERE room_id = " + votingRoomId);
  }
}
