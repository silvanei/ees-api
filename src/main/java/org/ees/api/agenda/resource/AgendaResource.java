package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.Calendar;
import org.ees.api.agenda.entity.Event;
import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.infra.auth.TokenUtil;
import org.ees.api.agenda.resource.bean.Agendamento;
import org.ees.api.agenda.resource.bean.DateParam;
import org.ees.api.agenda.service.AgendaService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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

    private DateTimeFormatter dtfPadrao = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Inject
    private AgendaService agendaService;

    @Context
    private UriInfo uriInfo;

    @HeaderParam("Authorization")
    private String authString;

    public AgendaResource(Integer salaoId) {
        this.salaoId = salaoId;
    }

    @GET
    @RolesAllowed({Perfil.SALAO_ADMIN, Perfil.SALAO_PROFISSIONAL})
    public Response eventos(
            @QueryParam("inicio") DateParam inicio,
            @QueryParam("fim") DateParam fim
    ) {

        TokenUtil.permissionSla(authString, salaoId);

        if(null == inicio) {
            inicio = new DateParam(new DateTime().toString(dtfPadrao));
        }

        if (null == fim) {
            fim = inicio;
        }

        Calendar calendar = agendaService.getDay(salaoId, inicio.getDate(), fim.getDate());

        return Response.ok(calendar).build();
    }

    @GET
    @Path("/{agendaId}")
    @RolesAllowed({Perfil.SALAO_ADMIN, Perfil.SALAO_PROFISSIONAL})
    public Response evento(@PathParam("agendaId") Integer agendaId) {
        TokenUtil.permissionSla(authString, salaoId);

        Event event = agendaService.findById(salaoId, agendaId);

        return Response.ok(event).build();
    }

    @POST
    @RolesAllowed({Perfil.SALAO_ADMIN, Perfil.SALAO_PROFISSIONAL})
    public Response agendar(Agendamento agendamento) {
        TokenUtil.permissionSla(authString, salaoId);

        Event event = agendaService.add(salaoId, agendamento);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(event.getId()));
        return Response.created(builder.build()).entity(event).build();
    }

    @PUT
    @Path("/{agendaId}")
    @RolesAllowed({Perfil.SALAO_ADMIN, Perfil.SALAO_PROFISSIONAL})
    public Response agendar(
            @PathParam("agendaId") Integer agendaId,
            Agendamento agendamento
    ) {
        TokenUtil.permissionSla(authString, salaoId);

        Event event = agendaService.update(salaoId, agendaId, agendamento);

        return Response.ok().entity(event).build();
    }

    public Integer getSalaoId() {
        return salaoId;
    }

    public void setSalaoId(Integer salaoId) {
        this.salaoId = salaoId;
    }
}
