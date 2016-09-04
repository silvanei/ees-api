package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.ClienteSalao;
import org.ees.api.agenda.entity.Funcionario;
import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.infra.db.CollectionPaginated;
import org.ees.api.agenda.infra.resource.collection.ClienteSalaoCollection;
import org.ees.api.agenda.service.ClienteSalaoService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * Created by silvanei on 29/08/16.
 */
public class ClienteSalaoResource {

    private Integer salaoId;

    @Inject
    private ClienteSalaoService clienteSalaoService;

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

    public Integer getSalaoId() {
        return salaoId;
    }

    public void setSalaoId(Integer salaoId) {
        this.salaoId = salaoId;
    }
}
