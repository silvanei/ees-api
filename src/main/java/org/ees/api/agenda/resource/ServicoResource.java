package org.ees.api.agenda.resource;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.ees.api.agenda.entity.Servico;
import org.ees.api.agenda.infra.db.CollectionPaginated;
import org.ees.api.agenda.resource.bean.ServicoFilterBean;
import org.ees.api.agenda.service.ServicoService;

public class ServicoResource {
	
	@Inject
	private ServicoService servicoService;

	@GET
	@RolesAllowed("SALAO_ADMIN")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response listAllServicos(
			@BeanParam ServicoFilterBean filterBean
	) {

		CollectionPaginated<Servico> servicos = servicoService.findByIdSalao(filterBean.getIdSalao(), filterBean.getLimit(), filterBean.getOffset());

		return Response.ok(servicos).build();
	}

	@POST
	@RolesAllowed("SALAO_ADMIN")
	public Response addServico(@PathParam("salaoId") Integer salaoId, Servico servico) {

		Servico newServico = servicoService.insert(salaoId, servico);

		return Response.status(Status.CREATED).entity(newServico).build();
	}
}
