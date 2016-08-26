package org.ees.api.agenda.resource;

import com.nimbusds.jose.JOSEException;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.ees.api.agenda.entity.DiaDaSemana;
import org.ees.api.agenda.entity.HorarioTrabalho;
import org.ees.api.agenda.entity.Perfil;

import java.sql.Time;

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
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response getIt() throws Exception {
        DiaDaSemana diaDaSemana = new DiaDaSemana(DiaDaSemana.DOMINGO);
        HorarioTrabalho horarioTrabalho = new HorarioTrabalho(
            diaDaSemana,
            new Time(10, 51, 0),
            new Time(10, 51, 0),
            new Time(10, 51, 0),
            new Time(10, 51, 0)
        );

        return Response.ok(horarioTrabalho).build();
		
//    	System.out.println(Perfil.SALAO_ADMIN);
//        System.out.println(securityContext.isUserInRole(Perfil.SALAO_ADMIN));
//        return "Got it!";
    }
}
