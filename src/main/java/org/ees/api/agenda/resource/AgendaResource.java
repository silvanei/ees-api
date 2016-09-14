package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.Calendar;
import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.resource.bean.Agendamento;
import org.ees.api.agenda.resource.bean.DateParam;
import org.ees.api.agenda.service.AgendaService;
import org.joda.time.DateTime;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by silvanei on 10/09/16.
 */
public class AgendaResource {

    private Integer salaoId;

    @Inject
    private AgendaService agendaService;

    @Context
    private UriInfo uriInfo;

    public AgendaResource(Integer salaoId) {
        this.salaoId = salaoId;
    }

    @GET
    @Path("/{start}")
    @RolesAllowed({Perfil.SALAO_ADMIN, Perfil.SALAO_PROFISSIONAL})
    public Response clientes(
            @PathParam("start") DateParam start
    ) {

        Calendar calendar = agendaService.getDay(salaoId, start.getDate(), start.getDate());

        return Response.ok(calendar).build();
    }

    @GET
    @Path("/{start}/{end}")
    @RolesAllowed({Perfil.SALAO_ADMIN, Perfil.SALAO_PROFISSIONAL})
    public Response clientes(
            @PathParam("start") DateParam start,
            @PathParam("end") DateParam end
    ) {

        Calendar calendar = agendaService.getDay(salaoId, start.getDate(), end.getDate());

        return Response.ok(calendar).build();
    }

    @POST
    @Path("/{dia}")
    @RolesAllowed({Perfil.SALAO_ADMIN, Perfil.SALAO_PROFISSIONAL})
    public Response agendar(
            @PathParam("dia") DateParam dia,
            Agendamento agendamento
    ) {

        agendaService.add(
                salaoId,
                agendamento.getClienteId(),
                agendamento.getServicoId(),
                agendamento.getFuncionarioId(),
                dia.getDate(),
                agendamento.getHora(),
                agendamento.getObservacao()
        );

        Calendar calendar = agendaService.getDay(salaoId, dia.getDate(), dia.getDate());

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        return Response.created(builder.build()).entity(calendar).build();
    }
}
