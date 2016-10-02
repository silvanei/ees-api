package org.ees.api.agenda.infra.resource.collection;

import org.ees.api.agenda.entity.ReservaCliente;
import org.ees.api.agenda.resource.ClienteResource;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by silvanei on 02/10/16.
 */
public class ReservaClienteCollection {

    @InjectLink(
            resource = ClienteResource.class,
            method = "reservas",
            style = InjectLink.Style.ABSOLUTE,
            rel = "self"
    )
    private Link link;

    private List<ReservaCliente> items;

    public ReservaClienteCollection(List<ReservaCliente> items) {
        this.items = items;
    }

    public List<ReservaCliente> getItems() {
        return items;
    }
}
