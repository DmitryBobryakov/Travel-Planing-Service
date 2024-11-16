package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DatabaseConfig {
    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres@localhost");
        config.setUsername("postgres");
        config.setPassword("JavaMTS");
        //включение кэширования подготовленных выражений
        config.addDataSourceProperty("cachePrepStmts", "true");
        //размера кэша для подготовленных выражений
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        //максимальный размер запроса
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
