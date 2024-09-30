package org.mtech.csa.parking.tenant;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * The TenantAwareRoutingSource class extends AbstractRoutingDataSource from Spring Framework and overrides
 * the determineCurrentLookupKey method to provide tenant-specific data source lookup keys based on the current tenant context.
 */
public class TenantAwareRoutingSource extends AbstractRoutingDataSource {

    /**
     * Determines the current lookup key which represents the tenant identifier.
     * This method retrieves the current tenant ID from the TenantContext using the getCurrentTenant method.
     *
     * @return An object representing the current tenant identifier.
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContext.getCurrentTenant();
    }
}

