package org.ees.api.agenda.infra.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by silvanei on 23/07/16.
 */
@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter{

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        System.out.println("Request Headers: " + containerRequestContext.getHeaders());
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        System.out.println("Response Headers: " + containerRequestContext.getHeaders());
    }
}
