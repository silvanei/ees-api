package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.entity.Salao;
import org.ees.api.agenda.resource.bean.DadosSalao;
import org.ees.api.agenda.service.DadosSalaoService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DadosResource {

	@Inject
	private DadosSalaoService dadosSalaoService;

	private Integer salaoId;

	public DadosResource(Integer salaoId) {
		this.salaoId = salaoId;
	}

	@GET
	@RolesAllowed({Perfil.SALAO_ADMIN, Perfil.SALAO_PROFISSIONAL})
	public Response dadosSalao() {

		Salao salao = dadosSalaoService.findById(salaoId);

		return Response.ok(salao).build();
	}

	@PUT
	@RolesAllowed(Perfil.SALAO_ADMIN)
	public Response atualizaDadosSalao(DadosSalao dadosSalao) {

		Salao salao = dadosSalaoService.atualizaDadosSalao(salaoId, dadosSalao);

		return Response.ok(salao).build();
	}

	public Integer getSalaoId() {
		return salaoId;
	}

	public void setSalaoId(Integer salaoId) {
		this.salaoId = salaoId;
	}
}
