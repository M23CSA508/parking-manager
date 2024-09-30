package org.mtech.csa.parking.interceptor;

import org.mtech.csa.parking.tenant.TenantContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The TenantInterceptorImpl class serves as an interceptor for web requests in Spring MVC applications.
 * It extracts the 'Tenant-ID' header from incoming HTTP requests and sets it as the current tenant context before processing the request.
 */
@Component
public class TenantInterceptorImpl implements HandlerInterceptor {

    /**
     * Invoked before the actual handler method is invoked.
     * Extracts the 'Tenant-ID' header from the given request and sets it as the current tenant context.
     *
     * @param request  the current HTTP request
     * @param response the current HTTP response
     * @param handler  the handler that would handle the request
     * @throws Exception if any error occurs during execution
     * @return true to continue with the next interceptors or the handler itself; false to abort further processing
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tenant = request.getHeader("Tenant-ID"); // Tenant identifier (e.g., tenant1, tenant2)
        if (tenant != null) {
            TenantContext.setCurrentTenant(tenant);
        }
        return true;
    }

    /**
     * Invoked after the handler method has been called but before rendering the view.
     * Clears the current tenant context to prevent potential cross-tenant data leaks.
     *
     * @param request  the current HTTP request
     * @param response the current HTTP response
     * @param handler  the handler that handled the request
     * @param ex       any exception thrown by the handler
     * @throws Exception if any error occurs during execution
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TenantContext.clear();
    }
}