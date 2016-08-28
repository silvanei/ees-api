package org.ees.api.agenda.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/v1/salao")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SalaoResource {

	@Context
	private ResourceContext rc;

	@Path("/{salaoId}/dados")
	public DadosResource dadosSalao(
			@PathParam("salaoId") Integer salaoId
	) {
		return rc.initResource(new DadosResource(salaoId));
	}


	@Path("/{salaoId}/servico")
	public ServicoResource servicoSalao(
			@PathParam("salaoId") Integer salaoId
	) {
		return rc.initResource(new ServicoResource(salaoId));
	}

	@Path("/{salaoId}/funcionario")
	public FuncionarioResource funcionarioSalao(
			@PathParam("salaoId") Integer salaoId
	) {
		return rc.initResource(new FuncionarioResource(salaoId));
	}
}
