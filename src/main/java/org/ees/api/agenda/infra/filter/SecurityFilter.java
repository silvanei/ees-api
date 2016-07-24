package org.ees.api.agenda.infra.filter;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.infra.auth.AuthUtils;
import org.ees.api.agenda.infra.service.AcessoServiceImpl;
import org.ees.api.agenda.infra.service.PerfilServiceImpl;
import org.ees.api.agenda.service.AcessoService;
import org.ees.api.agenda.service.PerfilService;
import org.joda.time.DateTime;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by silvanei on 24/07/16.
 */

@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final String EXPIRE_ERROR_MSG = "Token has expired";
    private static final String JWT_ERROR_MSG = "Unable to parse JWT";
    private static final String JWT_INVALID_MSG = "Invalid JWT token";


    private AcessoService acessoService = new AcessoServiceImpl();
    private PerfilService perfilService = new PerfilServiceImpl();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        SecurityContext originalContext = requestContext.getSecurityContext();
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || authHeader.isEmpty() || authHeader.split(" ").length != 2) {
            Authorizer authorizer = new Authorizer(new Perfil(), "",
                    originalContext.isSecure());
            requestContext.setSecurityContext(authorizer);
        } else {
            JWTClaimsSet claimSet;
            try {
                claimSet = (JWTClaimsSet) AuthUtils.decodeToken(authHeader);
            } catch (ParseException e) {
                throw new IOException(JWT_ERROR_MSG);
            } catch (JOSEException e) {
                throw new IOException(JWT_INVALID_MSG);
            }

            // ensure that the token is not expired
            if (new DateTime(claimSet.getExpirationTime()).isBefore(DateTime.now())) {
                throw new IOException(EXPIRE_ERROR_MSG);
            } else {
                Acesso acesso = acessoService.findById(Integer.parseInt(claimSet.getSubject()));
                Authorizer authorizer = new Authorizer(acesso.getPerfil(), acesso.getEmail(),
                        true);
                requestContext.setSecurityContext(authorizer);
            }
        }

    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext response) throws IOException {
        response.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
        response.getHeaders().putSingle("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
        response.getHeaders().putSingle("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }


    public static class Authorizer implements SecurityContext {

        //List<Perfil> roles;//Usuário com varios perfis
        Perfil perfil; //Usuario com um perfil
        String username;
        boolean isSecure;

        public Authorizer(Perfil perfil, String username, boolean isSecure) {
            this.perfil = perfil;
            this.username = username;
            this.isSecure = isSecure;
        }

        @Override
        public Principal getUserPrincipal() {
            return new User(username);
        }

        @Override
        public boolean isUserInRole(String role) {
            return perfil.equals(role);
            //return roles.contains(new Perfil(role));//Muchos a muchos
        }

        @Override
        public boolean isSecure() {
            return isSecure;
        }

        @Override
        public String getAuthenticationScheme() {
            return "JWT Authentication";
        }
    }

    public static class User implements Principal {

        String name;

        public User(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
