package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.Imagen;
import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.infra.auth.TokenUtil;
import org.ees.api.agenda.service.ImageFileService;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.*;

/**
 * Created by silvanei on 15/09/16.
 */
public class ImageFileResource {

    private Integer salaoId;

    @Context
    private UriInfo uriInfo;

    @HeaderParam("Authorization")
    private String authString;

    @Inject
    private ImageFileService imageFileService;

    public ImageFileResource(Integer salaoId) {
        this.salaoId = salaoId;
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response create(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail
    ) {

        TokenUtil.permissionSla(authString, salaoId);

        imageFileService.upload(Integer.toString(salaoId), uploadedInputStream, fileDetail);

        String encodedString = imageFileService.base64Encode(salaoId);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        return Response.created(builder.build()).entity(new Imagen(encodedString)).build();
    }

    @GET
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response file() {
        TokenUtil.permissionSla(authString, salaoId);

        String encodedString = "";

        encodedString = imageFileService.base64Encode(salaoId);
        if (null == encodedString) {
            encodedString = ImageFileService.IMG_DEFAULT;
        }

        return Response.ok().entity(new Imagen(encodedString)).build();

    }

    @DELETE
    @RolesAllowed(Perfil.SALAO_ADMIN)
    public Response delete() {
        TokenUtil.permissionSla(authString, salaoId);

        imageFileService.delete(salaoId);
        return Response.noContent().build();

    }

    public Integer getSalaoId() {
        return salaoId;
    }

    public void setSalaoId(Integer salaoId) {
        this.salaoId = salaoId;
    }
}
