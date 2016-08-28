package org.ees.api.agenda.resource;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.entity.Funcionario;
import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.entity.Salao;
import org.ees.api.agenda.resource.bean.RegistrarSalaoBean;
import org.ees.api.agenda.service.RegistrarSalaoService;

@Path("/v1/registrar-salao")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegistrarSalaoResource {

	private Integer salaoId;

	@Inject
	private RegistrarSalaoService salaoService;

    @Context
    private UriInfo uriInfo;

	@POST
	public Response addSalao(RegistrarSalaoBean registrarSalao) {

		Salao salao = new Salao(registrarSalao.getNomeSalao(), registrarSalao.getTelefoneSalao());

		Acesso acesso = new Acesso(Perfil.SALAO_ADMIN, registrarSalao.getEmailAdministradorSalao(),
				registrarSalao.getSenhaAdministradorSalao());

		Funcionario administrador = new Funcionario(registrarSalao.getNomeAdministradorSalao());

		Salao newSalao = salaoService.registrarSalao(salao, administrador, acesso);

		this.salaoId = newSalao.getId();

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(newSalao.getId()));
        return Response.created(builder.build()).entity(newSalao).build();

	}

	public Integer getSalaoId() {
		return salaoId;
	}

	public void setSalaoId(Integer salaoId) {
		this.salaoId = salaoId;
	}
}
