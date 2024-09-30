package org.mtech.csa.parking.tenant;

/**
 * The TenantContext class manages the contextual information related to tenants within an application.
 */
public class TenantContext {
    /**
     * A thread-local variable used to store the current tenant identifier during runtime.
     */
    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

    /**
     * Retrieves the current tenant identifier from the thread-local storage.
     *
     * @return the current tenant identifier as a String.
     */
    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    /**
     * Sets the current tenant identifier into the thread-local storage.
     *
     * @param tenant the tenant identifier to be stored.
     */
    public static void setCurrentTenant(String tenant) {
        currentTenant.set(tenant);
    }

    /**
     * Clears the current tenant identifier from the thread-local storage.
     */
    public static void clear() {
        currentTenant.remove();
    }
}