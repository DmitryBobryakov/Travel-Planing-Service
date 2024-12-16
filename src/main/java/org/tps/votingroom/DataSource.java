package org.tps.votingroom;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
  private static HikariConfig config = new HikariConfig();
  private static HikariDataSource ds;
  private static Config conf = ConfigFactory.load();

  static {
    config.setJdbcUrl(conf.getString("app.database.url"));
    config.setUsername(conf.getString("app.database.user"));
    config.setPassword(conf.getString("app.database.password"));
    config.addDataSourceProperty("cachePrepStmts", "true");
    config.addDataSourceProperty("prepStmtCacheSize", "250");
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    ds = new HikariDataSource(config);
  }

  public DataSource() {
  }

  public static Connection getConnection() throws SQLException {
    return ds.getConnection();
  }
}
