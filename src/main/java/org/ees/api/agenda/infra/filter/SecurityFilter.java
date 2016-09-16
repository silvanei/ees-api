package org.ees.api.agenda.infra.filter;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.infra.auth.Parameters;
import org.ees.api.agenda.infra.auth.TokenUtil;
import org.ees.api.agenda.infra.exceptions.UnAuthorizedException;
import org.ees.api.agenda.service.AcessoService;
import org.joda.time.DateTime;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;

/**
 * Created by silvanei on 24/07/16.
 */

@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter, ContainerResponseFilter {

    public static final String EXPIRE_ERROR_MSG = "Token has expired";
	public static final String FORBIDDEN_ERROR_MSG = "Access denied";
    public static final String JWT_ERROR_MSG = "Unable to parse JWT";
    public static final String JWT_INVALID_MSG = "Invalid JWT token";

	private AcessoService acessoService;

	@Inject
	public SecurityFilter(AcessoService acessoService) {
		this.acessoService = acessoService;
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		SecurityContext originalContext = requestContext.getSecurityContext();
		String authHeader = requestContext.getHeaderString(TokenUtil.AUTH_HEADER_KEY);

		if (authHeader == null || authHeader.isEmpty() || authHeader.split(" ").length != 2) {
			Authorizer authorizer = new Authorizer("", "", originalContext.isSecure());
			requestContext.setSecurityContext(authorizer);
			return;
		}

		JWTClaimsSet claimSet;
		try {
			claimSet = (JWTClaimsSet) TokenUtil.decodeToken(authHeader);
		} catch (ParseException e) {
			throw new UnAuthorizedException(JWT_ERROR_MSG);
		} catch (JOSEException e) {
			throw new UnAuthorizedException(JWT_INVALID_MSG);
		}

		// ensure that the token is not expired
		if (new DateTime(claimSet.getExpirationTime()).isBefore(DateTime.now())) {

			if(requestContext.getUriInfo().getPath().equals("v1/token")) {
				Authorizer authorizer = new Authorizer("", "", originalContext.isSecure());
				requestContext.setSecurityContext(authorizer);
				return;
			}

			throw new UnAuthorizedException(EXPIRE_ERROR_MSG);
		}

		MultivaluedMap<String, String> pathParameters = requestContext.getUriInfo().getPathParameters();

		if (pathParameters.get(Parameters.SALAO_ID.toString()) != null) {
			int salaoId = Integer.parseInt(pathParameters.get(Parameters.SALAO_ID.toString()).get(0));
			int sla = Integer.parseInt(claimSet.getCustomClaim(Parameters.SLA.toString()).toString());
			if(salaoId != sla) {
				throw new ForbiddenException(FORBIDDEN_ERROR_MSG);
			}
		}

		if (pathParameters.get(Parameters.CLIENTE_ID.toString()) != null) {
			int clienteId = Integer.parseInt(pathParameters.get(Parameters.CLIENTE_ID.toString()).get(0));
			int cli = Integer.parseInt(claimSet.getCustomClaim(Parameters.CLI.toString()).toString());
			if(clienteId != cli) {
				throw new ForbiddenException(FORBIDDEN_ERROR_MSG);
			}
		}


		Acesso acesso = acessoService.findById(Integer.parseInt(claimSet.getSubject()));
		Authorizer authorizer = new Authorizer(acesso.getPerfil(), acesso.getEmail(),
				originalContext.isSecure());
		requestContext.setSecurityContext(authorizer);

	}

	@Override
	public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext response)
			throws IOException {
		response.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
		response.getHeaders().putSingle("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
		response.getHeaders().putSingle("Access-Control-Allow-Headers", "Content-Type, Authorization");
	}

	public static class Authorizer implements SecurityContext {

		// List<Perfil> roles;//Usuário com varios perfis
		String perfil; // Usuario com um perfil
		String username;
		boolean isSecure;

		public Authorizer(String perfil, String username, boolean isSecure) {
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
			// return roles.contains(new Perfil(role));//Muchos a muchos
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
