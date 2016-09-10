package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.Calendar;
import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.resource.bean.DateParam;
import org.ees.api.agenda.service.AgendaService;
import org.joda.time.DateTime;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by silvanei on 10/09/16.
 */
public class AgendaResource {

    private Integer salaoId;

    @Inject
    private AgendaService agendaService;

    public AgendaResource(Integer salaoId) {
        this.salaoId = salaoId;
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
}
