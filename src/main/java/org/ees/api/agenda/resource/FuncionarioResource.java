package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.*;
import org.ees.api.agenda.infra.db.CollectionPaginated;
import org.ees.api.agenda.infra.resource.collection.FuncionarioCollection;
import org.ees.api.agenda.service.AcessoService;
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

    private Integer salaoId;

    @Inject
    private FuncionarioService funcionarioService;

    @Context
    private UriInfo uriInfo;

    public FuncionarioResource(Integer salaoId) {
        this.salaoId = salaoId;
    }

    @GET
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response funcionarios(
            @QueryParam("limit") @DefaultValue("5") int limit,
            @QueryParam("offset") @DefaultValue("0") int offset
    ) {

        CollectionPaginated<Funcionario> funcionarios = funcionarioService.findByIdSalao(salaoId, limit, offset);

        FuncionarioCollection funcionarioColection = new FuncionarioCollection(funcionarios);

        return Response.ok(funcionarioColection).build();
    }

    @GET
    @Path("/{funcionarioId}")
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response funcionario(
            @PathParam("funcionarioId") Integer funcionarioId
    ) {

        Funcionario funcionario = funcionarioService.findById(salaoId, funcionarioId);

        return Response.ok(funcionario).build();
    }

    @POST
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response createFuncionario(Funcionario funcionario) {

        Funcionario newFuncionario = funcionarioService.insert(salaoId, funcionario);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(newFuncionario.getId()));
        return Response.created(builder.build()).entity(newFuncionario).build();
    }

    @PUT
    @Path("/{funcionarioId}")
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response updateFuncionario(
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
            @PathParam("funcionarioId") Integer funcionarioId
    ) {

        funcionarioService.delete(salaoId, funcionarioId);

        return Response.noContent().build();
    }

    @POST
    @Path("/{funcionarioId}/servico/{servicoId}")
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response adicionarServico(
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
            @PathParam("funcionarioId") Integer funcionarioId,
            @PathParam("servicoId") Integer servicoId
    ) {

        funcionarioService.removeServico(salaoId, funcionarioId, servicoId);

        return Response.noContent().build();
    }

    @POST
    @Path("/{funcionarioId}/horario-trabalho/{diaDaSemana}")
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response adicionarHorario(
            @PathParam("funcionarioId") Integer funcionarioId,
            @PathParam("diaDaSemana") int diaDaSemana,
            HorarioTrabalho horario
    ) throws Exception {

        HorarioTrabalho newHorario = funcionarioService.addHorario(salaoId, funcionarioId, new DiaDaSemana(diaDaSemana), horario);

        return Response.ok().entity(newHorario).build();
    }

    @PUT
    @Path("/{funcionarioId}/horario-trabalho/{diaDaSemana}")
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response atualizarHorario(
            @PathParam("funcionarioId") Integer funcionarioId,
            @PathParam("diaDaSemana") int diaDaSemana,
            HorarioTrabalho horario
    ) throws Exception {

        HorarioTrabalho newHorario = funcionarioService.updateHorario(salaoId, funcionarioId, new DiaDaSemana(diaDaSemana), horario);

        return Response.ok().entity(newHorario).build();
    }

    @DELETE
    @Path("/{funcionarioId}/horario-trabalho/{diaDaSemana}")
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response excluirHorario(
            @PathParam("funcionarioId") Integer funcionarioId,
            @PathParam("diaDaSemana") int diaDaSemana
    ) throws Exception {

        funcionarioService.deleteHorario(salaoId, funcionarioId, new DiaDaSemana(diaDaSemana));

        return Response.noContent().build();
    }

    @POST
    @Path("/{funcionarioId}/acesso")
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response acesso(
            @PathParam("funcionarioId") Integer funcionarioId,
            Acesso acesso
    ) {

        Acesso newAcesso = funcionarioService.addAcesso(salaoId, funcionarioId, acesso);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        return Response.created(builder.build()).entity(newAcesso).build();
    }


    public Integer getSalaoId() {
        return salaoId;
    }

    public void setSalaoId(Integer salaoId) {
        this.salaoId = salaoId;
    }
}
