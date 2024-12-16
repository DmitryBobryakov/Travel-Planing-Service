package org.tps.autorization;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConfig {
    private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);
    private static HikariDataSource dataSource;

    static {
        try (FileInputStream fis = new FileInputStream("database.properties")) {
            Properties properties = new Properties();
            properties.load(fis);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(properties.getProperty("jdbc:postgresql://localhost:5432/postgres"));
            config.setUsername(properties.getProperty("postgres"));
            config.setPassword(properties.getProperty("JavaMTS"));
            // включение кэширования подготовленных выражений
            config.addDataSourceProperty("cachePrepStmts", properties.getProperty("db.cachePrepStmts", "true"));
            // размера кэша для подготовленных выражений
            config.addDataSourceProperty("prepStmtCacheSize", properties.getProperty("db.prepStmtCacheSize", "250"));
            // максимальный размер запроса
            config.addDataSourceProperty("prepStmtCacheSqlLimit", properties.getProperty("db.prepStmtCacheSqlLimit", "2048"));

            dataSource = new HikariDataSource(config);
        } catch (IOException e) {
            log.error("Ошибка загрузки конфигурации базы данных из файла database.properties",e);
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
