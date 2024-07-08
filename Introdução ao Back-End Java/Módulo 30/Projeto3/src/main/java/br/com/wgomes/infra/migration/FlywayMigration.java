package br.com.wgomes.infra.migration;

import org.flywaydb.core.Flyway;

public class FlywayMigration {
    private FlywayMigration() {
    }

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
