package org.ees.api.agenda.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/")
public class ServicoResource {

	@POST
	public Response addSalao() {
		return Response.status(Status.CREATED).entity(null).build();
	}
}
