package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.Calendar;
import org.ees.api.agenda.entity.Event;
import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.resource.bean.Agendamento;
import org.ees.api.agenda.resource.bean.DateParam;
import org.ees.api.agenda.service.AgendaService;
import org.joda.time.DateTime;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
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
    @Path("/{start}/{end}")
    @RolesAllowed({Perfil.SALAO_ADMIN, Perfil.SALAO_PROFISSIONAL})
    public Response eventoPeriodo(
            @PathParam("start") DateParam start,
            @PathParam("end") DateParam end
    ) {

        Calendar calendar = agendaService.getDay(salaoId, start.getDate(), end.getDate());

        return Response.ok(calendar).build();
    }

    @GET
    @Path("/{dia}")
    @RolesAllowed({Perfil.SALAO_ADMIN, Perfil.SALAO_PROFISSIONAL})
    public Response eventos(
            @PathParam("dia") DateParam dia
    ) {

        Calendar calendar = agendaService.getDay(salaoId, dia.getDate(), dia.getDate());

        return Response.ok(calendar).build();
    }

    @GET
    @Path("/{dia}/event/{eventId}")
    @RolesAllowed({Perfil.SALAO_ADMIN, Perfil.SALAO_PROFISSIONAL})
    public Response evento(
            @PathParam("dia") DateParam dia,
            @PathParam("eventId") Integer eventId
    ) {

        Event event = agendaService.getDay(salaoId, dia.getDate(), eventId);

        return Response.ok(event).build();
    }

    @POST
    @Path("/{dia}/event")
    @RolesAllowed({Perfil.SALAO_ADMIN, Perfil.SALAO_PROFISSIONAL})
    public Response agendar(
            @PathParam("dia") DateParam dia,
            Agendamento agendamento
    ) {

        Integer eventId = agendaService.add(
                salaoId,
                agendamento.getClienteId(),
                agendamento.getServicoId(),
                agendamento.getFuncionarioId(),
                dia.getDate(),
                agendamento.getHora(),
                agendamento.getObservacao()
        );

        Event event = agendaService.getDay(salaoId, dia.getDate(), eventId);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path( "/event/" + Integer.toString(event.getId()));
        return Response.created(builder.build()).entity(event).build();
    }

    @PUT
    @Path("/{dia}/event/{eventId}")
    @RolesAllowed({Perfil.SALAO_ADMIN, Perfil.SALAO_PROFISSIONAL})
    public Response agendar(
            @PathParam("dia") DateParam dia,
            @PathParam("eventId") Integer eventId,
            Agendamento agendamento
    ) {

        Event event = agendaService.update(salaoId, eventId, dia.getDate(), agendamento);

        return Response.ok().entity(event).build();
    }

    public Integer getSalaoId() {
        return salaoId;
    }

    public void setSalaoId(Integer salaoId) {
        this.salaoId = salaoId;
    }
}
