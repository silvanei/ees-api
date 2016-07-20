package org.s3.agenda.api.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.s3.agenda.api.model.entity.Salao;

@Path("/registrar-salao")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegistrarSalao {

	@POST
	public Salao addSalao(Salao salao) {
		return salao;
	}
}
