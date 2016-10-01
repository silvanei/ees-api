package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.ClienteSalao;
import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.infra.auth.TokenUtil;
import org.ees.api.agenda.infra.db.CollectionPaginated;
import org.ees.api.agenda.infra.resource.collection.ClienteSalaoCollection;
import org.ees.api.agenda.service.ClienteSalaoService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by silvanei on 29/08/16.
 */
public class ClienteSalaoResource {

    private Integer salaoId;

    @Inject
    private ClienteSalaoService clienteSalaoService;

    @Context
    private UriInfo uriInfo;

    @HeaderParam("Authorization")
    private String authString;

    public ClienteSalaoResource(Integer salaoId) {
        this.salaoId = salaoId;
    }

    @GET
    @RolesAllowed({Perfil.SALAO_ADMIN, Perfil.SALAO_PROFISSIONAL})
    public Response clientes(
            @QueryParam("limit") int limit,
            @QueryParam("offset") int offset
    ) {

        TokenUtil.permissionSla(authString, salaoId);

        CollectionPaginated<ClienteSalao> clientes;

        if (0 == limit) {
            clientes = clienteSalaoService.get(salaoId);
        } else {
            clientes = clienteSalaoService.get(salaoId, limit, offset);
        }

        ClienteSalaoCollection clienteSalaoCollection = new ClienteSalaoCollection(clientes);

        return Response.ok(clienteSalaoCollection).build();
    }

    @GET
    @Path("/{clienteSalaoId}")
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response cliente(
            @PathParam("clienteSalaoId") Integer clienteSalaoId
    ) {

        TokenUtil.permissionSla(authString, salaoId);

        ClienteSalao clienteSalao = clienteSalaoService.findById(salaoId, clienteSalaoId);

        return Response.ok(clienteSalao).build();
    }

    @POST
    @RolesAllowed({Perfil.SALAO_ADMIN, Perfil.SALAO_PROFISSIONAL})
    public Response create(ClienteSalao data) {

        ClienteSalao clienteSalao = clienteSalaoService.create(salaoId, data);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(clienteSalao.getId()));
        return Response.created(builder.build()).entity(clienteSalao).build();
    }

    @PUT
    @Path("/{clienteSalaoId}")
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response update(
            ClienteSalao data,
            @PathParam("clienteSalaoId") Integer clienteSalaoId
    ) {

        TokenUtil.permissionSla(authString, salaoId);

        ClienteSalao clienteSalao = clienteSalaoService.update(salaoId, clienteSalaoId, data);

        return Response.ok().entity(clienteSalao).build();
    }

    @DELETE
    @Path("/{clienteSalaoId}")
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response delete(
            @PathParam("clienteSalaoId") Integer clienteSalaoId
    ) {

        TokenUtil.permissionSla(authString, salaoId);

        clienteSalaoService.delete(salaoId, clienteSalaoId);

        return Response.noContent().build();
    }

    public Integer getSalaoId() {
        return salaoId;
    }

    public void setSalaoId(Integer salaoId) {
        this.salaoId = salaoId;
    }
}
