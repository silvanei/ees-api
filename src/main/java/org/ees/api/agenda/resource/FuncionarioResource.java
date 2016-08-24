package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.Funcionario;
import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.infra.db.CollectionPaginated;
import org.ees.api.agenda.infra.resource.collection.FuncionarioCollection;
import org.ees.api.agenda.service.FuncionarioService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by silvanei on 14/08/16.
 */
public class FuncionarioResource {

    @Inject
    FuncionarioService funcionarioService;

    @GET
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response funcionarios(
            @PathParam("salaoId") Integer idSalao,
            @QueryParam("limit") @DefaultValue("5") int limit,
            @QueryParam("offset") @DefaultValue("0") int offset
    ) {

        CollectionPaginated<Funcionario> funcionarios = funcionarioService.findByIdSalao(idSalao, limit, offset);

        FuncionarioCollection funcionarioColection = new FuncionarioCollection(funcionarios);

        return Response.ok(funcionarioColection).build();
    }

    @GET
    @Path("/{funcionarioId}")
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response funcionario(
            @PathParam("salaoId") Integer salaoId,
            @PathParam("funcionarioId") Integer funcionarioId
    ) {

        Funcionario funcionario = funcionarioService.findById(salaoId, funcionarioId);

        return Response.ok(funcionario).build();
    }

    @POST
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response createFuncionario(
            @PathParam("salaoId") Integer salaoId,
            Funcionario funcionario,
            @Context UriInfo uriInfo
    ) {

        Funcionario newFuncionario = funcionarioService.insert(salaoId, funcionario);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(newFuncionario.getId()));
        return Response.created(builder.build()).entity(newFuncionario).build();
    }

    @PUT
    @Path("/{funcionarioId}")
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response updateFuncionario(
            @PathParam("salaoId") Integer salaoId,
            @PathParam("funcionarioId") Integer funcionarioId,
            Funcionario funcionario
    ) {

        Funcionario newFuncionario = funcionarioService.update(salaoId, funcionarioId, funcionario);

        return Response.ok().entity(newFuncionario).build();
    }

    @DELETE
    @Path("/{funcionarioId}")
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response deleteFuncionario(
            @PathParam("salaoId") Integer salaoId,
            @PathParam("funcionarioId") Integer funcionarioId
    ) {

        funcionarioService.delete(salaoId, funcionarioId);

        return Response.noContent().build();
    }

    @POST
    @Path("/{funcionarioId}/servico/{servicoId}")
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response adicionarServico(
            @PathParam("salaoId") Integer salaoId,
            @PathParam("funcionarioId") Integer funcionarioId,
            @PathParam("servicoId") Integer servicoId
    ) {

        Funcionario funcionario = funcionarioService.addServico(salaoId, funcionarioId, servicoId);

        return Response.ok().entity(funcionario).build();
    }

    @DELETE
    @Path("/{funcionarioId}/servico/{servicoId}")
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response removerServico(
            @PathParam("salaoId") Integer salaoId,
            @PathParam("funcionarioId") Integer funcionarioId,
            @PathParam("servicoId") Integer servicoId
    ) {

        funcionarioService.removeServico(salaoId, funcionarioId, servicoId);

        return Response.noContent().build();
    }

}
