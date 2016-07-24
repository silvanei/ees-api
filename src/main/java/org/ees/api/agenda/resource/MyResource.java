package org.ees.api.agenda.resource;

import com.nimbusds.jose.JOSEException;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    @Context
    SecurityContext securityContext;

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("user")
    public String getIt() throws JOSEException {

        //System.out.println(securityContext.isUserInRole("SALAO_ADMIN"));
        return "Got it!";
    }
}
