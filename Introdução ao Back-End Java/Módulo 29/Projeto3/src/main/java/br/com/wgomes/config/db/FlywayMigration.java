package br.com.wgomes.config.db;

import org.flywaydb.core.Flyway;

public class FlywayMigration {
    public static void execute(String url, String user, String password, String location) {
        Flyway flyway = Flyway.configure()
                .dataSource(url, user, password)
                .schemas("PUBLIC")
                .locations(location)
                .baselineOnMigrate(true)
                .load();
        flyway.migrate();
    }
}
