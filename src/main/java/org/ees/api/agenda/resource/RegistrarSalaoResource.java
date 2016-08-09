package org.ees.api.agenda.resource;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.entity.Funcionario;
import org.ees.api.agenda.entity.PerfilEnum;
import org.ees.api.agenda.entity.Salao;
import org.ees.api.agenda.infra.service.RegistrarSalaoServiceImpl;
import org.ees.api.agenda.resource.bean.RegistrarSalaoBean;
import org.ees.api.agenda.service.RegistrarSalaoService;

@Path("/v1/registrar-salao")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegistrarSalaoResource {

	@Inject
	private RegistrarSalaoService salaoService;

	@POST
	public Response addSalao(RegistrarSalaoBean registrarSalao) {

		Salao salao = new Salao(registrarSalao.getNomeSalao(), registrarSalao.getTelefoneSalao());

		Acesso acesso = new Acesso(PerfilEnum.SALAO_ADMIN.name(), registrarSalao.getEmailAdministradorSalao(),
				registrarSalao.getSenhaAdministradorSalao());

		Funcionario administrador = new Funcionario(registrarSalao.getNomeAdministradorSalao());

		Salao newSalao = salaoService.registrarSalao(salao, administrador, acesso);

		return Response.status(Status.CREATED).entity(newSalao).build();

	}
}
