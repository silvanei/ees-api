package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.ClienteSalao;
import org.ees.api.agenda.entity.Funcionario;
import org.ees.api.agenda.entity.Perfil;
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

    public ClienteSalaoResource(Integer salaoId) {
        this.salaoId = salaoId;
    }

    @GET
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response clientes(
            @QueryParam("limit") int limit,
            @QueryParam("offset") int offset
    ) {

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

        ClienteSalao clienteSalao = clienteSalaoService.get(salaoId, clienteSalaoId);

        return Response.ok(clienteSalao).build();
    }

    @POST
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response create(ClienteSalao data) {

        ClienteSalao clienteSalao = clienteSalaoService.create(salaoId, data);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(clienteSalao.getId()));
        return Response.created(builder.build()).entity(clienteSalao).build();
    }

    public Integer getSalaoId() {
        return salaoId;
    }

    public void setSalaoId(Integer salaoId) {
        this.salaoId = salaoId;
    }
}
