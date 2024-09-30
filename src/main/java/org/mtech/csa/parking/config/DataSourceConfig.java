package org.mtech.csa.parking.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.mtech.csa.parking.tenant.TenantAwareRoutingSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * The DataSourceConfig class configures and provides access to multiple database connections based on the current tenant.
 */
@Configuration
public class DataSourceConfig {

    /**
     * Creates a DataSource bean that routes requests to different databases depending on the current tenant.
     *
     * @return A configured instance of TenantAwareRoutingSource which acts as a proxy for accessing various tenants' databases.
     */
    @Bean
    public DataSource dataSource() {
        // Create map of tenant-specific data sources
        Map<Object, Object> dataSourceMap = new HashMap<>();

        // For each tenant, create a DataSource (here you can load from properties or dynamically)
        DataSource tenant1DataSource = createDataSource("jdbc:postgresql:tenant1db", "postgres", "admin");
        DataSource tenant2DataSource = createDataSource("jdbc:postgresql:tenant2db", "postgres", "admin");

        dataSourceMap.put("tenant1", tenant1DataSource);
        dataSourceMap.put("tenant2", tenant2DataSource);

        // Create routing source
        TenantAwareRoutingSource tenantRoutingDataSource = new TenantAwareRoutingSource();
        tenantRoutingDataSource.setTargetDataSources(dataSourceMap);
        tenantRoutingDataSource.setDefaultTargetDataSource(tenant1DataSource);

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
    private DataSource createDataSource(String url, String username, String password) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        return new HikariDataSource(hikariConfig);
    }
}
