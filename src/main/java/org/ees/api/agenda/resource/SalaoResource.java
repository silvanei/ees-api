package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.service.AcessoService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

@Path("/v1/salao")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SalaoResource {

	@Context
	private ResourceContext rc;

    @Inject
    AcessoService acessoService;

	@Path("/{salaoId}/dados")
	public DadosResource dadosSalao(
			@PathParam("salaoId") Integer salaoId
	) {
		return rc.initResource(new DadosResource(salaoId));
	}


	@Path("/{salaoId}/servico")
	public ServicoResource servicoSalao(
			@PathParam("salaoId") Integer salaoId
	) {
		return rc.initResource(new ServicoResource(salaoId));
	}

	@Path("/{salaoId}/funcionario")
	public FuncionarioResource funcionarioSalao(
			@PathParam("salaoId") Integer salaoId
	) {
		return rc.initResource(new FuncionarioResource(salaoId));
	}

	@Path("/{salaoId}/cliente")
	public ClienteSalaoResource clienteSalao(
			@PathParam("salaoId") Integer salaoId
	) {
		return rc.initResource(new ClienteSalaoResource(salaoId));
	}

    @GET
    @Path("/{salaoId}/acesso")
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response acesso(
            @PathParam("salaoId") Integer salaoId
    ) {

        List<Acesso> acessos = acessoService.findByIdSalao(salaoId);

        return Response.ok(acessos).build();
    }
}
