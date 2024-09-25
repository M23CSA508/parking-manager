package org.mtech.csa.parking.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.mtech.csa.parking.tenant.TenantAwareRoutingSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        // Create map of tenant-specific data sources
        Map<Object, Object> dataSourceMap = new HashMap<>();

        // For each tenant, create a DataSource (here you can load from properties or dynamically)
        DataSource tenant1DataSource = createDataSource("jdbc:h2:mem:tenant1db", "sa", "");
        DataSource tenant2DataSource = createDataSource("jdbc:h2:mem:tenant2db", "sa", "");

        dataSourceMap.put("tenant1", tenant1DataSource);
        dataSourceMap.put("tenant2", tenant2DataSource);

        // Create routing source
        TenantAwareRoutingSource tenantRoutingDataSource = new TenantAwareRoutingSource();
        tenantRoutingDataSource.setTargetDataSources(dataSourceMap);
        tenantRoutingDataSource.setDefaultTargetDataSource(tenant1DataSource);

        return tenantRoutingDataSource;
    }

    private DataSource createDataSource(String url, String username, String password) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        return new HikariDataSource(hikariConfig);
    }
}
