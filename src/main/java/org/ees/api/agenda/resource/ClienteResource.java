package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.ClienteApp;
import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.service.ClienteAppService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/cliente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteResource {

    private ClienteAppService clienteAppService;

    @Inject
    public ClienteResource(ClienteAppService clienteAppService) {
        this.clienteAppService = clienteAppService;
    }

    @GET
    @Path("/{clienteId}")
    @RolesAllowed(Perfil.CLIENTE)
	public Response cliente(@PathParam("clienteId") Integer clienteId) {

        ClienteApp clienteApp = clienteAppService.findById(clienteId);

        return Response.ok().entity(clienteApp).build();
	}
}