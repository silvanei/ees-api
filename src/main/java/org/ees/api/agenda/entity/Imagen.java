package org.ees.api.agenda.entity;

import org.ees.api.agenda.resource.ImageFileResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;

/**
 * Created by silvanei on 15/09/16.
 */
public class Imagen {

    private String encodedImage;

    @InjectLink(
            resource = ImageFileResource.class,
            method = "file",
            style = InjectLink.Style.ABSOLUTE,
            bindings = {
                    @Binding(name = "id", value = "${resource.salaoId}")
            },
            rel = "self"
    )
    private Link link;

    public Imagen() {
    }

    public Imagen(String encodedImage) {
        setEncodedImage(encodedImage);
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }
}
