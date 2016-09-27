package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.Cidade;
import org.ees.api.agenda.entity.Estado;
import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.service.EnderecoService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by silvanei on 25/09/16.
 */


@Path("/v1/estado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstadoResource {

    @Inject
    private EnderecoService enderecoService;

    @GET
    public Response estado() {

        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(86400); // 1 dia

        List<Estado> estados = enderecoService.getEstados();

        return Response.ok(estados).cacheControl(cacheControl).build();
    }

    @GET
    @Path("/{estadoId}")
    public Response cidade(@PathParam("estadoId") Integer estadoId) {

        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(86400); // 1 dia
        Estado estado = enderecoService.getEstado(estadoId);

        return Response.ok(estado).cacheControl(cacheControl).build();
    }

}
