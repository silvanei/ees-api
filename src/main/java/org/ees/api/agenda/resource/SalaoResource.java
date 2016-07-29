package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.Salao;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/salao")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SalaoResource {


	@Path("/{salaoId}/dados")
	public DadosResource atualizaSalao() {
		return new DadosResource();
	}


	@Path("/{salaoId}/servico")
	public ServicoResource addSalao() {
		return new ServicoResource();
	}
}
