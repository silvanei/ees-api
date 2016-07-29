package org.ees.api.agenda.resource;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.ees.api.agenda.entity.Servico;
import org.ees.api.agenda.infra.service.ServicoServiceImpl;
import org.ees.api.agenda.service.ServicoService;


public class ServicoResource {
	
	private ServicoService servicoService = new ServicoServiceImpl();

	@POST
	@RolesAllowed("SALAO_ADMIN")
	public Response addSalao(
			@PathParam("salaoId") Integer salaoId,
			Servico servico
	) {
		
		Servico newServico = servicoService.insert(salaoId, servico);
		
		return Response.status(Status.CREATED).entity(newServico).build();
	}
}
