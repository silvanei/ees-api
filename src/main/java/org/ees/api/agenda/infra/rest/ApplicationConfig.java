package org.ees.api.agenda.infra.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.ws.rs.ApplicationPath;

/**
 * Created by silvanei on 23/07/16.
 */

@ApplicationPath("/")
public class ApplicationConfig extends ResourceConfig{

    public ApplicationConfig() {
        //packages("org.ees.api.agenda.resource;org.ees.api.agenda.infra.auth");
        register(RolesAllowedDynamicFeature.class);
    }
}
