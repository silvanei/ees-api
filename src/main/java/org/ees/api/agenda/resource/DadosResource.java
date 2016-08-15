package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.entity.Salao;
import org.ees.api.agenda.infra.service.DadosSalaoServiceImpl;
import org.ees.api.agenda.resource.bean.DadosSalao;
import org.ees.api.agenda.service.DadosSalaoService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DadosResource {

	@Inject
	private DadosSalaoService dadosSalaoService;

	@PUT
	@RolesAllowed(Perfil.SALAO_ADMIN)
	public Response atualizaDadosSalao(
			@PathParam("salaoId") Integer salaoId,
			DadosSalao dadosSalao
	) {

		Salao salao = dadosSalaoService.atualizaDadosSalao(salaoId, dadosSalao);

		return Response
				.status(Response.Status.CREATED)
				.entity(salao)
				.build();
	}
}
