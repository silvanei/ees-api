package org.ees.api.agenda.infra.rest;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * Created by silvanei on 23/07/16.
 */

@ApplicationPath("/")
public class ApplicationConfig extends ResourceConfig{

    public ApplicationConfig() {
    	register(new ApplicationBinder());
        packages("org.ees.api.agenda");
        register(RolesAllowedDynamicFeature.class);
    }
}
