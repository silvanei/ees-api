package org.ees.api.agenda.infra.resource.collection;

import org.ees.api.agenda.entity.Servico;
import org.ees.api.agenda.infra.db.CollectionPaginated;
import org.ees.api.agenda.resource.ServicoResource;
import org.ees.api.agenda.resource.collection.CollectionResponse;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by silvanei on 06/08/16.
 */
public class ServicoCollection extends CollectionResponse {

    @InjectLinks({
            @InjectLink(
                    resource = ServicoResource.class,
                    method = "servicos",
                    style = InjectLink.Style.ABSOLUTE,
                    rel = "self"
            ),

            @InjectLink(
                    resource = ServicoResource.class,
                    style = InjectLink.Style.ABSOLUTE,
                    method = "servicos",
                    condition = "${instance.offset + instance.limit < instance.count}",
                    bindings = {
                            @Binding(name = "offset", value = "${instance.offset + instance.limit}"),
                            @Binding(name = "limit", value = "${instance.limit}")
                    },
                    rel = "next"
            ),

            @InjectLink(
                    resource = ServicoResource.class,
                    style = InjectLink.Style.ABSOLUTE,
                    method = "servicos",
                    condition = "${instance.offset - instance.limit >= 0}",
                    bindings = {
                            @Binding(name = "offset", value = "${instance.offset - instance.limit}"),
                            @Binding(name = "limit", value = "${instance.limit}")
                    },
                    rel = "prev"
            )
    })
    protected List<Link> links;

    public ServicoCollection(CollectionPaginated<Servico> collectionPaginated) {
        super(collectionPaginated);
    }
}
