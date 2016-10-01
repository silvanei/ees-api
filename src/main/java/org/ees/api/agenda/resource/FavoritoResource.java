package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.entity.Salao;
import org.ees.api.agenda.infra.auth.TokenUtil;
import org.ees.api.agenda.service.ClienteAppService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * Created by silvanei on 24/09/16.
 */
public class FavoritoResource {

    private Integer clienteId;

    @Context
    private UriInfo uriInfo;

    @HeaderParam("Authorization")
    private String authString;

    private ClienteAppService clienteAppService;

    public FavoritoResource(Integer clienteId, ClienteAppService clienteAppService) {
        this.clienteId = clienteId;
        this.clienteAppService = clienteAppService;
    }

    @GET
    @RolesAllowed(Perfil.CLIENTE)
    public Response favorito() {

        TokenUtil.permissionCli(authString, clienteId);

        List<Salao> saloes = clienteAppService.getFavoritos(clienteId);

        return Response.ok().entity(saloes).build();
    }

    @POST
    @Path("/salao/{favoritoId}")
    @RolesAllowed(Perfil.CLIENTE)
    public Response addFavorito(@PathParam("favoritoId") Integer salaoId) {

        TokenUtil.permissionCli(authString, clienteId);

        Integer id = clienteAppService.addFavorito(clienteId, salaoId);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        return Response.created(builder.build()).entity(null).build();
    }

    @DELETE
    @Path("/salao/{favoritoId}")
    @RolesAllowed(Perfil.CLIENTE)
    public Response removeFavorito(@PathParam("favoritoId") Integer salaoId) {

        TokenUtil.permissionCli(authString, clienteId);

        clienteAppService.removeFavorito(clienteId, salaoId);

        return Response.noContent().build();
    }
}
