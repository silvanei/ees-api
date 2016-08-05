package org.ees.api.agenda.infra.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import org.ees.api.agenda.resource.ServicoResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by silvanei on 30/07/16.
 */

public class Metadata {

    @InjectLinks({
            @InjectLink(
                    resource = ServicoResource.class,
                    method = "listAllServicos",
                    style = InjectLink.Style.ABSOLUTE,
                    rel = "self"
            ),

            @InjectLink(
                    resource = ServicoResource.class,
                    style = InjectLink.Style.ABSOLUTE,
                    method = "listAllServicos",
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
                    method = "listAllServicos",
                    condition = "${instance.offset - instance.limit >= 0}",
                    bindings = {
                            @Binding(name = "offset", value = "${instance.offset - instance.limit}"),
                            @Binding(name = "limit", value = "${instance.limit}")
                    },
                    rel = "prev"
            )
    })

    private List<Link> links;

    private Integer count;
    private Integer offset;
    private Integer limit;

    public Metadata(Integer count, Integer offset, Integer limit) {
        this.count = count;
        this.offset = offset;
        this.limit = limit;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getOffset() {
        return offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
