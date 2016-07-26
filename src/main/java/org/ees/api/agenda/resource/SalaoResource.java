package org.ees.api.agenda.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/salao")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SalaoResource {

	@Path("/{salaoId}/servico")
	public ServicoResource addSalao() {
		return new ServicoResource();
	}
}
