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
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/cliente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteResource {

    private ClienteAppService clienteAppService;

    private DadosSalaoService dadosSalao;

    @Context
    private ResourceContext rc;

    @HeaderParam("Authorization")
    private String authString;

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
    @Path("/{clienteId}/salao/{id}/servico")
    @RolesAllowed(Perfil.CLIENTE)
    public Response salao(
            @PathParam("clienteId") Integer clienteId,
            @PathParam("id") Integer salaoId
    ) {

        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(5); // 1 dia

        Salao salao = dadosSalao.servicos(salaoId);

        return Response.ok().entity(salao).cacheControl(cacheControl).build();
    }

    @Path("/{clienteId}/favorito")
    public FavoritoResource favorito(@PathParam("clienteId") Integer clienteId) {
        return rc.initResource(new FavoritoResource(clienteId, clienteAppService));
    }

    public Integer getSalaoId() {
        return null;
    }
}