package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.entity.Funcionario;
import org.ees.api.agenda.entity.Salao;
import org.ees.api.agenda.infra.service.RegistrarSalaoServiceImpl;
import org.ees.api.agenda.resource.bean.RegistrarSalaoBean;
import org.ees.api.agenda.service.RegistrarSalaoService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/registrar-salao")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegistrarSalaoResource {

	private RegistrarSalaoService salaoService = new RegistrarSalaoServiceImpl();

	@POST
	public Response addSalao(RegistrarSalaoBean registrarSalao) {


		Salao salao = new Salao(registrarSalao.getNomeSalao(), registrarSalao.getTelefoneSalao());

		Acesso acesso = new Acesso(1, registrarSalao.getEmailAdministradorSalao(),
				registrarSalao.getSenhaAdministradorSalao());

		Funcionario administrador = new Funcionario(registrarSalao.getNomeAdministradorSalao());

		Salao newSalao = salaoService.registrarSalao(salao, administrador, acesso);

		return Response
				.status(Status.CREATED)
				.entity(newSalao)
				.build();
		
	}
}
