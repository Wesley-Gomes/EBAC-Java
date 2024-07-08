package br.com.wgomes.domain.factories;

import br.com.wgomes.exceptions.ConnectionException;
import br.com.wgomes.exceptions.EnvironmentException;
import br.com.wgomes.infra.migration.FlywayMigration;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class ConnectionFactory {
    private static final Dotenv dotenv = Dotenv.load();
    private static Connection connection;
    private static String environment;
    private static String testsMigrationLocation;

    private ConnectionFactory() {
    }

    public static Connection getConnection() throws ConnectionException, SQLException, EnvironmentException {
        getEnvironmentConfig();
        if (environment == null) throw new ConnectionException("Environment not found");
        if (environment.equals("test") || environment.equals("dev")) {
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

    public static void closeConnection() throws ConnectionException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }
    }

    private static void getEnvironmentConfig() throws EnvironmentException {
        try (InputStream input = ConnectionFactory.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                Logger logger = Logger.getLogger(ConnectionFactory.class.getName());
                logger.log(null, "Sorry, unable to find config.properties");
                return;
            }
            Properties prop = new Properties();
            prop.load(input);
            testsMigrationLocation = prop.getProperty("db_test_migrations_location");
            environment = prop.getProperty("env");
        } catch (Exception ex) {
            throw new EnvironmentException(ex.getMessage());
        }
    }

    private static Connection initH2Connection() throws ConnectionException, EnvironmentException {
        try {
            String url = dotenv.get("H2_URL");
            String user = dotenv.get("H2_USER");
            String password = dotenv.get("H2_PASSWORD");
            if (url == null || user == null) throw new EnvironmentException("H2 connection config not found");
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }
    }

    private static void runMigrationsOnTestsDatabase() throws EnvironmentException {
        String url = dotenv.get("H2_URL");
        String user = dotenv.get("H2_USER");
        String password = dotenv.get("H2_PASSWORD");
        if (url == null || user == null) throw new EnvironmentException("H2 connection config not found");
        FlywayMigration.execute(url, user, password, testsMigrationLocation);
    }
}
