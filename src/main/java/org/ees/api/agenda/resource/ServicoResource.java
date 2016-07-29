package org.ees.api.agenda.resource;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.ees.api.agenda.entity.Servico;
import org.ees.api.agenda.infra.service.ServicoServiceImpl;
import org.ees.api.agenda.service.ServicoService;


public class ServicoResource {
	
	private ServicoService servicoService = new ServicoServiceImpl();

	@GET
	@RolesAllowed("SALAO_ADMIN")
	@Path("/")
	public Response listAllServicos(@PathParam("salaoId") Integer idSalao) {
		
		List<Servico> servicos = servicoService.findByIdSalao(idSalao);
		
		return Response.status(Status.OK).entity(servicos).build();
	}
	
	@POST
	@RolesAllowed("SALAO_ADMIN")
	public Response addServico(
			@PathParam("salaoId") Integer salaoId,
			Servico servico
	) {
		
		Servico newServico = servicoService.insert(salaoId, servico);
		
		return Response.status(Status.CREATED).entity(newServico).build();
	}
}
