package org.ees.api.agenda.resource;

import com.nimbusds.jose.JOSEException;
import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.entity.Salao;
import org.ees.api.agenda.infra.auth.TokenUtil;
import org.ees.api.agenda.infra.filter.SecurityFilter;
import org.ees.api.agenda.resource.bean.DadosSalao;
import org.ees.api.agenda.service.AcessoService;
import org.ees.api.agenda.service.DadosSalaoService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.*;
import java.text.ParseException;
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
    @RolesAllowed({Perfil.CLIENTE})
    public Response saloes(
            @QueryParam("nome") String nomeSalao
    ) {

        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(5); // 1 dia

        List<Salao> saloes = dadosSalaoService.findAllVisiveNoApp(nomeSalao);

        return Response.ok().entity(saloes).cacheControl(cacheControl).build();
    }

	@GET
	@Path("/{id}")
	@RolesAllowed({Perfil.SALAO_ADMIN, Perfil.SALAO_PROFISSIONAL, Perfil.CLIENTE})
	public Response salao(@PathParam("id") Integer salaoId) throws ParseException, JOSEException {

        Salao salao = null;

        if (securityContext.isUserInRole(Perfil.CLIENTE)) {
            salao = dadosSalaoService.findById(salaoId, true);
        } else {
            permission(salaoId);
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
	) throws ParseException, JOSEException {

        permission(salaoId);

        Salao salao = dadosSalaoService.atualizaDadosSalao(salaoId, dadosSalao);
		return Response.ok(salao).build();
	}

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

	@Path("/{salaoId}/agenda")
	public AgendaResource agenda(
			@PathParam("salaoId") Integer salaoId
	) {
		return rc.initResource(new AgendaResource(salaoId));
	}

	@Path("/{salaoId}/horario-disponivel")
	public HorarioDisponivelResource horarioDisponivel(
            @PathParam("salaoId") Integer salaoId
	) {
		return rc.initResource(new HorarioDisponivelResource(salaoId));
	}

	@Path("/{salaoId}/image")
	public ImageFileResource image(@PathParam("salaoId") Integer salaoId) {
		return rc.initResource(new ImageFileResource(salaoId));
	}

    private void permission(Integer salaoId) throws ParseException, JOSEException {
        if (! TokenUtil.getSla(authString).equals(salaoId)) {
            throw new ForbiddenException(SecurityFilter.FORBIDDEN_ERROR_MSG);
        }
    }
}
