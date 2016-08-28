package org.ees.api.agenda.infra.exceptions;

import org.ees.api.agenda.resource.bean.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by silvanei on 28/08/16.
 */
@Provider
public class ConflictExceptionMapper implements ExceptionMapper<ConflictException> {

    @Override
    public Response toResponse(ConflictException e) {

        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), Response.Status.CONFLICT.getStatusCode());
        return Response.status(Response.Status.CONFLICT).entity(errorMessage).build();

    }
}
