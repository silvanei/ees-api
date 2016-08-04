package org.ees.api.agenda.infra.application;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * Created by silvanei on 23/07/16.
 */

@ApplicationPath("/")
public class Config extends ResourceConfig{

    public Config() {
        register(new Binder());
        register(RolesAllowedDynamicFeature.class);
        register(DeclarativeLinkingFeature.class);
        packages(true, "org.ees.api.agenda");
    }
}
