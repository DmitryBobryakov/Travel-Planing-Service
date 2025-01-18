package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl( "jdbc:postgresql://localhost:5438/postgres" );
        config.setUsername( "postgres" );
        config.setPassword( "12345" );
        ds = new HikariDataSource( config );
    }

    public DataSource() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}