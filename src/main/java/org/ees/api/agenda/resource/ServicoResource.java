package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.Servico;
import org.ees.api.agenda.infra.db.CollectionPaginated;
import org.ees.api.agenda.service.ServicoService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ServicoResource {

	@Inject
	private ServicoService servicoService;

	@GET
	@RolesAllowed("SALAO_ADMIN")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response listAllServicos(
			@PathParam("salaoId") Integer idSalao,
			@QueryParam("limit") @DefaultValue("5") int limit,
			@QueryParam("offset") @DefaultValue("0") int offset
	) {

		CollectionPaginated<Servico> servicos = servicoService.findByIdSalao(idSalao, limit, offset);

		return Response.ok(servicos).build();
	}

	@GET
	@RolesAllowed("SALAO_ADMIN")
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{servicoId}")
	public Response servico(
			@PathParam("salaoId") Integer idSalao,
			@PathParam("servicoId") Integer servicoId
	) {

		Servico servico = servicoService.findById(idSalao, servicoId);

		return Response.ok(servico).build();
	}

	@POST
	@RolesAllowed("SALAO_ADMIN")
	public Response addServico(@PathParam("salaoId") Integer salaoId, Servico servico) {

		Servico newServico = servicoService.insert(salaoId, servico);

		return Response.status(Status.CREATED).entity(newServico).build();
	}
}
