package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.entity.Servico;
import org.ees.api.agenda.infra.db.CollectionPaginated;
import org.ees.api.agenda.infra.resource.collection.ServicoCollection;
import org.ees.api.agenda.service.ServicoService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

public class ServicoResource {

    @Inject
    private ServicoService servicoService;

    @GET
    @RolesAllowed(Perfil.SALAO_ADMIN)
    @Produces({MediaType.APPLICATION_JSON})
    public Response servicos(
            @PathParam("salaoId") Integer idSalao,
            @QueryParam("limit") @DefaultValue("5") int limit,
            @QueryParam("offset") @DefaultValue("0") int offset
    ) {

        CollectionPaginated<Servico> servicos = servicoService.findByIdSalao(idSalao, limit, offset);

        ServicoCollection servicoColection = new ServicoCollection(servicos);

        return Response.ok(servicoColection).build();
    }

    @GET
    @Path("/{servicoId}")
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response servico(
            @PathParam("salaoId") Integer salaoId,
            @PathParam("servicoId") Integer servicoId
    ) {

        Servico servico = servicoService.findById(salaoId, servicoId);

        return Response.ok(servico).build();
    }

    @POST
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response createServico(
            @PathParam("salaoId") Integer salaoId,
            Servico servico,
            @Context UriInfo uriInfo
    ) {

        Servico newServico = servicoService.insert(salaoId, servico);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(newServico.getId()));
        return Response.created(builder.build()).entity(newServico).build();
    }

    @PUT
    @Path("/{servicoId}")
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response updateServico(
            @PathParam("salaoId") Integer salaoId,
            @PathParam("servicoId") Integer servicoId,
            Servico servico
    ) {

        Servico newServico = servicoService.update(salaoId, servicoId, servico);

        return Response.ok().entity(newServico).build();
    }

    @DELETE
    @Path("/{servicoId}")
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response deleteServico(
            @PathParam("salaoId") Integer salaoId,
            @PathParam("servicoId") Integer servicoId
    ) {

        servicoService.delete(salaoId, servicoId);

        return Response.noContent().build();
    }
}
