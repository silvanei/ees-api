package org.ees.api.agenda.infra.exceptions;

import org.ees.api.agenda.resource.bean.ErrorMessage;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {

	@Override
	public Response toResponse(ForbiddenException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), Status.FORBIDDEN.getStatusCode());
		return Response.status(Status.FORBIDDEN).entity(errorMessage).build();
	}
}
