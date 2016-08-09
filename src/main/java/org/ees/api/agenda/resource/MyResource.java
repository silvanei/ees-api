package org.ees.api.agenda.resource;

import com.nimbusds.jose.JOSEException;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.ees.api.agenda.entity.PerfilEnum;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/v1/myresource")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
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
    @RolesAllowed("SALAO_ADMIN")
    public String getIt() throws JOSEException {
		
    	System.out.println(PerfilEnum.SALAO_ADMIN.name());
        System.out.println(securityContext.isUserInRole(PerfilEnum.SALAO_ADMIN.name()));
        return "Got it!";
    }
}
