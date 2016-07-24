package org.ees.api.agenda.infra.exceptions;

import org.ees.api.agenda.resource.bean.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnAuthorizedExceptionMapper implements ExceptionMapper<UnAuthorizedException> {

	@Override
	public Response toResponse(UnAuthorizedException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), Status.UNAUTHORIZED.getStatusCode());
		return Response.status(Status.UNAUTHORIZED).entity(errorMessage).build();
	}

}
