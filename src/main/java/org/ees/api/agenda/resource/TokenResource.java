package org.ees.api.agenda.resource;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.infra.auth.Token;
import org.ees.api.agenda.infra.auth.TokenUtil;
import org.ees.api.agenda.infra.exceptions.DataNotFoundException;
import org.ees.api.agenda.infra.exceptions.UnAuthorizedException;
import org.ees.api.agenda.infra.filter.SecurityFilter;
import org.ees.api.agenda.resource.bean.UserBean;
import org.ees.api.agenda.service.AcessoService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;

/**
 * Created by silvanei on 24/07/16.
 */

@Path("/v1/token")
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
            Token token = TokenUtil.createToken(foundAcesso);

            return Response
                    .status(Response.Status.CREATED)
                    .entity(token)
                    .build();
        }

        throw new UnAuthorizedException("Usuário não autorizado");

    }

    @GET
    public Response refreshToken(@HeaderParam("Authorization") String authString) throws JOSEException, ParseException {

        if (null == authString) {
            throw new ForbiddenException(SecurityFilter.FORBIDDEN_ERROR_MSG);
        }

        String subject = TokenUtil.getSubject(authString);

        Acesso foundAcesso = acessoService.findById(Integer.valueOf(subject));
        Token token = TokenUtil.createToken(foundAcesso);

        return Response
                .status(Response.Status.CREATED)
                .entity(token)
                .build();
    }
}
