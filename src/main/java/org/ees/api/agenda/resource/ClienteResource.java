package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.ClienteApp;
import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.entity.Salao;
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

    private DadosSalaoService dadosSalao;

    @Context
    private ResourceContext rc;

    @Inject
    public ClienteResource(ClienteAppService clienteAppService, DadosSalaoService dadosSalao) {
        this.clienteAppService = clienteAppService;
        this.dadosSalao = dadosSalao;
    }

    @GET
    @Path("/{clienteId}")
    @RolesAllowed(Perfil.CLIENTE)
	public Response cliente(@PathParam("clienteId") Integer clienteId) {

        ClienteApp clienteApp = clienteAppService.findById(clienteId);

        return Response.ok().entity(clienteApp).build();
	}

    @GET
    @Path("/{clienteId}/salao")
    @RolesAllowed(Perfil.CLIENTE)
    public Response salao(
            @PathParam("clienteId") Integer clienteId,
            @QueryParam("salao") String nomeSalao
    ) {

        List<Salao> saloes = dadosSalao.findAll(nomeSalao);

        return Response.ok().entity(saloes).build();
    }

    @Path("/{clienteId}/favorito")
    public FavoritoResource favorito(@PathParam("clienteId") Integer clienteId) {
        return rc.initResource(new FavoritoResource(clienteId, clienteAppService));
    }
}