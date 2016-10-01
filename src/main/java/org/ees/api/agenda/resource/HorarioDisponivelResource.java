package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.HorarioDisponivel;
import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.infra.auth.TokenUtil;
import org.ees.api.agenda.resource.bean.DateParam;
import org.ees.api.agenda.service.HorarioDisponivelService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by silvanei on 12/09/16.
 */
public class HorarioDisponivelResource {

    @Inject
    private HorarioDisponivelService horarioDisponivelService;

    @HeaderParam("Authorization")
    private String authString;

    private Integer salaoId;

    public HorarioDisponivelResource(Integer salaoId) {
        this.salaoId = salaoId;
    }

    @GET
    @Path("/servico/{servicoId}/funcionario/{funcionarioId}/dia/{dia}")
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response funcinarios(
            @PathParam("servicoId") Integer servicoId,
            @PathParam("funcionarioId") Integer funcionarioId,
            @PathParam("dia") DateParam dia
    ) {

        TokenUtil.permissionSla(authString, salaoId);

        List<HorarioDisponivel> horariosDisponiveis = horarioDisponivelService.findBy(salaoId, servicoId, funcionarioId, dia.getDate());

        return Response.ok(horariosDisponiveis).build();
    }
}
