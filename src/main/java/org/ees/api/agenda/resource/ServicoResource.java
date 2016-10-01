package org.ees.api.agenda.resource;

import com.nimbusds.jose.JOSEException;
import org.ees.api.agenda.entity.Funcionario;
import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.entity.Servico;
import org.ees.api.agenda.infra.auth.TokenUtil;
import org.ees.api.agenda.infra.db.CollectionPaginated;
import org.ees.api.agenda.infra.resource.collection.ServicoCollection;
import org.ees.api.agenda.service.FuncionarioService;
import org.ees.api.agenda.service.ServicoService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.text.ParseException;
import java.util.List;

public class ServicoResource {

    @Inject
    private ServicoService servicoService;

    @Inject
    private FuncionarioService funcionarioService;

    @HeaderParam("Authorization")
    private String authString;

    private Integer salaoId;

    public ServicoResource(Integer salaoId) {
        this.salaoId = salaoId;
    }

    @GET
    @RolesAllowed({Perfil.SALAO_ADMIN, Perfil.SALAO_PROFISSIONAL})
    public Response servicos(
            @QueryParam("offset") int offset,
            @QueryParam("limit") int limit
    ) throws ParseException, JOSEException {

        TokenUtil.permissionSla(authString, salaoId);

        CollectionPaginated<Servico> servicos;

        if (0 == limit) {
            servicos = servicoService.findByIdSalao(salaoId);
        } else {
            servicos = servicoService.findByIdSalao(salaoId, limit, offset);
        }

        ServicoCollection servicoColection = new ServicoCollection(servicos);

        return Response.ok(servicoColection).build();
    }

    @GET
    @Path("/{servicoId}")
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response servico(
            @PathParam("servicoId") Integer servicoId
    ) throws ParseException, JOSEException {

        TokenUtil.permissionSla(authString, salaoId);

        Servico servico = servicoService.findById(salaoId, servicoId);

        return Response.ok(servico).build();
    }

    @POST
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response createServico(
            Servico servico,
            @Context UriInfo uriInfo
    ) throws ParseException, JOSEException {

        TokenUtil.permissionSla(authString, salaoId);

        Servico newServico = servicoService.insert(salaoId, servico);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(newServico.getId()));
        return Response.created(builder.build()).entity(newServico).build();
    }

    @PUT
    @Path("/{servicoId}")
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response updateServico(
            @PathParam("servicoId") Integer servicoId,
            Servico servico
    ) throws ParseException, JOSEException {

        TokenUtil.permissionSla(authString, salaoId);

        Servico newServico = servicoService.update(salaoId, servicoId, servico);

        return Response.ok().entity(newServico).build();
    }

    @DELETE
    @Path("/{servicoId}")
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response deleteServico(
            @PathParam("servicoId") Integer servicoId
    ) throws ParseException, JOSEException {

        TokenUtil.permissionSla(authString, salaoId);

        servicoService.delete(salaoId, servicoId);

        return Response.noContent().build();
    }

    @GET
    @Path("/{servicoId}/funcionario")
    @RolesAllowed({Perfil.SALAO_ADMIN, Perfil.SALAO_PROFISSIONAL})
    public Response funcinarios(
            @PathParam("servicoId") Integer servicoId
    ) throws ParseException, JOSEException {

        TokenUtil.permissionSla(authString, salaoId);

        List<Funcionario> funcionarios = funcionarioService.findByServicoId(salaoId, servicoId);

        return Response.ok(funcionarios).build();
    }

    public Integer getSalaoId() {
        return salaoId;
    }

    public void setSalaoId(Integer salaoId) {
        this.salaoId = salaoId;
    }
}
