package org.example;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddingVotingRoom {
    public static void addVoting(String owner_name, String date_finish,
                                 String[] friends_names,
                                 String[] trip_link, String[] trip_name) throws SQLException {
        String SQL = "INSERT INTO vote (name_owner, date_finish, name_friends, trip_link, trip_name) " + "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL)) {
            pst.setString(1, owner_name);
            pst.setString(2, date_finish);
            Array friendsArray = con.createArrayOf("text", friends_names);
            pst.setArray(3, friendsArray);
            Array trip_links_Array = con.createArrayOf("text", trip_link);
            pst.setArray(4, trip_links_Array);
            Array trip_names_Array = con.createArrayOf("text", trip_name);
            pst.setArray(5, trip_names_Array);
                pst.executeUpdate();
            } catch (SQLException e) {
            e.printStackTrace(); // выводим стек ошибки
        }
    }
}
