package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private String url = "jdbc:postgresql://localhost:5432/postgres"; 
    private String user = "postgres"; 
    private String password = "postgres";
    public List<Trip> getTrips() {
        List<Trip> trips = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM trips")) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                String dates = resultSet.getString("dates");
                String participants = resultSet.getString("participants");
                trips.add(new Trip(name, country, dates, participants));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trips;
    }
}
