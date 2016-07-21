package org.ees.api.agenda.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.entity.Funcionario;
import org.ees.api.agenda.entity.Salao;
import org.ees.api.agenda.infra.service.SalaoServiceImpl;
import org.ees.api.agenda.resource.bean.RegistrarSalaoBean;
import org.ees.api.agenda.service.SalaoService;
import org.jose4j.lang.JoseException;

@Path("/registrar-salao")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegistrarSalao {

	private SalaoService salaoService = new SalaoServiceImpl();

	@POST
	public Salao addSalao(RegistrarSalaoBean registrarSalao) throws JoseException {

		Salao salao = new Salao(registrarSalao.getNomeSalao(), registrarSalao.getTelefoneSalao());

		Acesso acesso = new Acesso(1, registrarSalao.getEmailAdministradorSalao(),
				registrarSalao.getSenhaAdministradorSalao());

		Funcionario administrador = new Funcionario(registrarSalao.getNomeAdministradorSalao(), acesso);

		return salaoService.registrarSalao(salao, administrador);
	}
}
