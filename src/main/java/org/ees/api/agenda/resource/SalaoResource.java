package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.entity.Salao;
import org.ees.api.agenda.infra.auth.TokenUtil;
import org.ees.api.agenda.resource.bean.DadosSalao;
import org.ees.api.agenda.service.AcessoService;
import org.ees.api.agenda.service.DadosSalaoService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/v1/salao")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SalaoResource {

	@Context
	private ResourceContext rc;

    @Inject
    AcessoService acessoService;

	@Context
	SecurityContext securityContext;

	@Inject
	private DadosSalaoService dadosSalaoService;

    @HeaderParam("Authorization")
    private String authString;

	@GET
	@Path("/{id}")
	@RolesAllowed({Perfil.SALAO_ADMIN, Perfil.SALAO_PROFISSIONAL, Perfil.CLIENTE})
	public Response salao(@PathParam("id") Integer salaoId)  {

        Salao salao = null;

        if (securityContext.isUserInRole(Perfil.CLIENTE)) {
            salao = dadosSalaoService.findById(salaoId, true);
        } else {
            TokenUtil.permissionSla(authString, salaoId);
            salao = dadosSalaoService.findById(salaoId);
        }

		return Response.ok(salao).build();
	}


    @PUT
	@Path("/{id}")
	@RolesAllowed({Perfil.SALAO_ADMIN})
	public Response atualizarSalao(
			@PathParam("id") Integer salaoId,
			DadosSalao dadosSalao
	)  {

        TokenUtil.permissionSla(authString, salaoId);

        Salao salao = dadosSalaoService.atualizaDadosSalao(salaoId, dadosSalao);
		return Response.ok(salao).build();
	}

	@Path("/{id}/servico")
	public ServicoResource servicoSalao(
			@PathParam("id") Integer salaoId
	) {
		return rc.initResource(new ServicoResource(salaoId));
	}

	@Path("/{id}/funcionario")
	public FuncionarioResource funcionarioSalao(
			@PathParam("id") Integer salaoId
	) {

		return rc.initResource(new FuncionarioResource(salaoId));
	}

	@Path("/{id}/cliente")
	public ClienteSalaoResource clienteSalao(
			@PathParam("id") Integer salaoId
	) {

		return rc.initResource(new ClienteSalaoResource(salaoId));
	}

    @GET
    @Path("/{id}/acesso")
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response acesso(
            @PathParam("id") Integer salaoId
    ) {
        TokenUtil.permissionSla(authString, salaoId);

        List<Acesso> acessos = acessoService.findByIdSalao(salaoId);

        return Response.ok(acessos).build();
    }

	@Path("/{id}/agenda")
	public AgendaResource agenda(
			@PathParam("id") Integer salaoId
	) {

		return rc.initResource(new AgendaResource(salaoId));
	}

	@Path("/{id}/image")
	public ImageFileResource image(@PathParam("id") Integer salaoId) {

		return rc.initResource(new ImageFileResource(salaoId));
	}
}
