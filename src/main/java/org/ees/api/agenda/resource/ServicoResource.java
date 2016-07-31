package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.Servico;
import org.ees.api.agenda.infra.db.CollectionPaginated;
import org.ees.api.agenda.infra.db.Metadata;
import org.ees.api.agenda.infra.service.ServicoServiceImpl;
import org.ees.api.agenda.resource.bean.ServicoFilterBean;
import org.ees.api.agenda.service.ServicoService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

public class ServicoResource {

	private ServicoService servicoService = new ServicoServiceImpl();

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
