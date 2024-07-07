package br.com.wgomes.domain.factories;

import br.com.wgomes.infra.migration.FlywayMigration;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static final Dotenv dotenv = Dotenv.load();
    private static Connection connection;
    private static String environment;
    private static String testsMigrationLocation;

    private ConnectionFactory() {
    }

    public static Connection getConnection() throws SQLException {
        getEnvironmentConfig();
        if (environment == null) throw new RuntimeException("Environment not found");
        if (environment.equals("test")) {
            if (connection == null) {
                runMigrationsOnTestsDatabase();
                connection = initH2Connection();
            } else if (connection.isClosed()) {
                connection = initH2Connection();
            }
            return connection;
        }
        return null;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getEnvironmentConfig() {
        try (InputStream input = ConnectionFactory.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            Properties prop = new Properties();
            prop.load(input);
            testsMigrationLocation = prop.getProperty("db_test_migrations_location");
            environment = prop.getProperty("env");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static Connection initH2Connection() {
        try {
            String url = dotenv.get("H2_URL");
            String user = dotenv.get("H2_USER");
            String password = dotenv.get("H2_PASSWORD");
            if (url == null || user == null) throw new RuntimeException("H2 connection not found");
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void runMigrationsOnTestsDatabase() {
        String url = dotenv.get("H2_URL");
        String user = dotenv.get("H2_USER");
        String password = dotenv.get("H2_PASSWORD");
        FlywayMigration.execute(url, user, password, testsMigrationLocation);
    }
}
