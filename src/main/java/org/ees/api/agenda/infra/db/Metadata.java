package org.ees.api.agenda.infra.db;

import org.ees.api.agenda.resource.ServicoResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;

/**
 * Created by silvanei on 30/07/16.
 */

public class Metadata {

    @InjectLink(
            resource=ServicoResource.class,
            method = "query",
            style = InjectLink.Style.ABSOLUTE_PATH,
            bindings = {
                    @Binding(name = "offset", value="${instance.offset}"),
                    @Binding(name = "limit", value="${instance.limit}")
            },
            rel = "self"
    )
    Link link;

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

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }
}
