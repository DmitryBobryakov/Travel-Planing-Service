package org.tps.votingroom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionService {

  private Connection connection;

  public Connection getConnection() throws SQLException {
    return connection == null ? DriverManager.getConnection("jdbc:postgresql://localhost:5438/postgres", "postgres",
        "12345") : connection;
  }
}
