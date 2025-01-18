package org.tps.autorization;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);
    private static HikariDataSource dataSource;

    static {
        Properties properties = new Properties();
        try (InputStream fis = DatabaseConfig.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (fis == null) {
                log.error("Файл database.properties не найден в classpath");
                throw new RuntimeException("Файл database.properties не найден");
            }
            properties.load(fis);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(properties.getProperty("db.url"));
            config.setUsername(properties.getProperty("db.username"));
            config.setPassword(properties.getProperty("db.password"));
            config.setDriverClassName(properties.getProperty("db.driver"));

            // Включение кэширования подготовленных выражений
            config.addDataSourceProperty("cachePrepStmts", properties.getProperty("db.cachePrepStmts", "true"));
            // Размер кэша для подготовленных выражений
            config.addDataSourceProperty("prepStmtCacheSize", properties.getProperty("db.prepStmtCacheSize", "250"));
            // Максимальный размер SQL запроса для кэша
            config.addDataSourceProperty("prepStmtCacheSqlLimit", properties.getProperty("db.prepStmtCacheSqlLimit", "2048"));

            dataSource = new HikariDataSource(config);
        } catch (IOException e) {
            log.error("Ошибка загрузки конфигурации базы данных из файла database.properties", e);
            throw new RuntimeException("Ошибка загрузки конфигурации базы данных", e);
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
