package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.ClienteApp;
import org.ees.api.agenda.entity.Event;
import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.entity.ReservaCliente;
import org.ees.api.agenda.infra.auth.TokenUtil;
import org.ees.api.agenda.infra.resource.collection.ReservaClienteCollection;
import org.ees.api.agenda.service.AgendaService;
import org.ees.api.agenda.service.ClienteAppService;
import org.ees.api.agenda.service.DadosSalaoService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/v1/cliente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteResource {

    private ClienteAppService clienteAppService;

    private AgendaService agendaService;

    @Context
    private ResourceContext rc;

    @HeaderParam("Authorization")
    private String authString;

    @Inject
    public ClienteResource(ClienteAppService clienteAppService, AgendaService agendaService) {
        this.clienteAppService = clienteAppService;
        this.agendaService = agendaService;
    }

    @GET
    @Path("/{id}")
    @RolesAllowed(Perfil.CLIENTE)
	public Response cliente(@PathParam("id") Integer clienteId) {

        TokenUtil.permissionCli(authString, clienteId);

        ClienteApp clienteApp = clienteAppService.findById(clienteId);

        return Response.ok().entity(clienteApp).build();
	}

    @Path("/{id}/favorito")
    public FavoritoResource favorito(@PathParam("id") Integer clienteId) {
        return rc.initResource(new FavoritoResource(clienteId, clienteAppService));
    }

    @GET
    @Path("/{id}/reserva")
    @RolesAllowed(Perfil.CLIENTE)
    public Response reservas(@PathParam("id") Integer clienteId) {

        TokenUtil.permissionCli(authString, clienteId);

        ReservaClienteCollection reservas = new ReservaClienteCollection(agendaService.findByClientId(clienteId));

        return Response.ok().entity(reservas).build();
    }

    @PUT
    @Path("/{id}/reserva/{reservaId}")
    @RolesAllowed(Perfil.CLIENTE)
    public Response reserva(
            @PathParam("id") Integer clienteId,
            @PathParam("reservaId") Integer reservaId
    ) {

        TokenUtil.permissionCli(authString, clienteId);

        agendaService.cancelarReservaCliente(clienteId, reservaId);

        return Response.ok().entity(null).build();
    }
}