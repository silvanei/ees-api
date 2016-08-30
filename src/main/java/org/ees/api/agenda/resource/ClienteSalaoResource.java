package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.ClienteSalao;
import org.ees.api.agenda.entity.Perfil;
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

        return Response.noContent().build();
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
}
