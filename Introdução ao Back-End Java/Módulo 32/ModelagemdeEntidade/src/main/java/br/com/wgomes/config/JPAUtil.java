package br.com.wgomes.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class JPAUtil {
    private static EntityManagerFactory emf;

    static {
        Dotenv dotenv = Dotenv.load();

        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.driver", dotenv.get("H2_DRIVER"));
        properties.put("javax.persistence.jdbc.url", dotenv.get("H2_URL"));
        properties.put("javax.persistence.jdbc.user", dotenv.get("H2_USER"));
        properties.put("javax.persistence.jdbc.password", dotenv.get("H2_PASSWORD"));

        emf = Persistence.createEntityManagerFactory("persistenceConfig", properties);
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}
