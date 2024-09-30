package org.mtech.csa.parking.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import org.mtech.csa.parking.tenant.TenantAwareRoutingSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The DataSourceConfig class configures and provides access to multiple database connections based on the current tenant.
 */
@AllArgsConstructor
@Configuration
public class DataSourceConfig {

    private Environment env;
    /**
     * Creates a DataSource bean that routes requests to different databases depending on the current tenant.
     *
     * @return A configured instance of TenantAwareRoutingSource which acts as a proxy for accessing various tenants' databases.
     */
    @Bean
    public DataSource dataSource() {
        // Create map of tenant-specific data sources
        Map<Object, Object> dataSourceMap = new HashMap<>();
        String tenants = env.getProperty("parking.manager.tenants");
        assert tenants != null;
        String[] tenantArr = tenants.split(",");

        for (String tenant : tenantArr) {
            if (!tenant.trim().isEmpty()) {
                String driverClassName = env.getProperty("spring."+tenant+".datasource.driver-class-name");
                String url = env.getProperty("spring."+tenant+".datasource.url");
                String username = env.getProperty("spring."+tenant+".datasource.username");
                String password = env.getProperty("spring."+tenant+".datasource.password");
                dataSourceMap.put(tenant, createDataSource(driverClassName, url, username, password));
            }
        }

        // Create routing source
        TenantAwareRoutingSource tenantRoutingDataSource = new TenantAwareRoutingSource();
        tenantRoutingDataSource.setTargetDataSources(dataSourceMap);
        tenantRoutingDataSource.setDefaultTargetDataSource(
                createDataSource(env.getProperty("spring.datasource.driver-class-name"),
                        env.getProperty("spring.datasource.url"),
                        env.getProperty("spring.datasource.username"),
                        env.getProperty("spring.datasource.password")));

        return tenantRoutingDataSource;
    }

    /**
     * Configures and returns a single DataSource object for a given database connection.
     *
     * @param url      The URL of the database server.
     * @param username The username used to connect to the database.
     * @param password The password used to connect to the database.
     * @return An instance of HikariDataSource configured according to the provided parameters.
     */
    private HikariDataSource createDataSource(String jdbcDriveClass, String url, String username, String password) {
        HikariConfig hikariConfig = createHikariConfig(jdbcDriveClass, url, username, password);
        return new HikariDataSource(hikariConfig);
    }

    private static HikariConfig createHikariConfig(String driverClass, String url, String username, String password) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverClass);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        return hikariConfig;
    }

}
