package org.ees.api.agenda.resource;

import com.nimbusds.jose.JOSEException;
import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.infra.auth.AuthUtils;
import org.ees.api.agenda.infra.auth.Token;
import org.ees.api.agenda.infra.exceptions.DataNotFoundException;

import org.ees.api.agenda.infra.exceptions.UnAuthorizedException;
import org.ees.api.agenda.infra.service.AcessoServiceImpl;
import org.ees.api.agenda.resource.bean.UserBean;
import org.ees.api.agenda.service.AcessoService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by silvanei on 24/07/16.
 */

@Path("/token")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TokenResource {

    private AcessoService acessoService;

    @Inject
    public TokenResource(AcessoService acessoService) {
        this.acessoService = acessoService;
    }

    @POST
    public Response createToken(UserBean user) throws JOSEException{
        Acesso foundAcesso;

        foundAcesso = acessoService.findByEmail(user.getEmail());
        if (foundAcesso == null) {
            throw new DataNotFoundException("Usuário não existe na dase de dados");
        } else if (user.getPassword().equals(foundAcesso.getSenha())) {
            Token token = AuthUtils.createToken(foundAcesso);

            return Response
                    .status(Response.Status.CREATED)
                    .entity(token)
                    .build();
        }

        throw new UnAuthorizedException("Usuário não autorizado");

    }
}
