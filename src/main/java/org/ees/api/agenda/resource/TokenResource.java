package org.ees.api.agenda.resource;

import com.nimbusds.jose.JOSEException;
import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.infra.Auth.AuthUtils;
import org.ees.api.agenda.infra.Auth.Token;
import org.ees.api.agenda.infra.exceptions.DataNotFoundException;

import org.ees.api.agenda.infra.exceptions.UnAuthorizedException;
import org.ees.api.agenda.infra.service.AcessoServiceImpl;
import org.ees.api.agenda.resource.bean.UserBean;
import org.ees.api.agenda.service.AcessoService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by silvanei on 24/07/16.
 */

@Path("/token")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TokenResource {

    private AcessoService acessoService = new AcessoServiceImpl();

    @POST
    public Response createToken(UserBean user) throws JOSEException{
        Acesso foundAcesso;

        foundAcesso = acessoService.findByEmail(user.getEmail());
        if (foundAcesso == null) {
            throw new DataNotFoundException("Usuário não existe na dase de dados");
        } else if (user.getPassword().equals(foundAcesso.getSenha())) {
            Token token = AuthUtils.createToken("localhost", foundAcesso);

            return Response
                    .status(Response.Status.CREATED)
                    .entity(token)
                    .build();
        }

        throw new UnAuthorizedException("Usuário não autorizado");

    }
}
