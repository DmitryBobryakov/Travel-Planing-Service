package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class AddingVotingRoom {

    public static void addVoting(String owner_name, String date_start, String date_finish, String[] friends_names, String[] trip_variants) throws SQLException{
        String SQL_QUERY = "INSERT INTO vote (name_owner, date_start, date_finish, name_friends, trip_variants) " +
                "VALUES (" + owner_name + ", " + date_start + ", " + date_finish + ", ARRAY" + Arrays.toString(friends_names) +
                ", ARRAY" + Arrays.toString(trip_variants) + ");";
        try (Connection con = DataSource.getConnection(); PreparedStatement pst = con.prepareStatement( SQL_QUERY );
             ResultSet rs = pst.executeQuery()) {
        }
    }

}
