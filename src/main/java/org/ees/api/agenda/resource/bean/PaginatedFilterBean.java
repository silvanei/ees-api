package org.ees.api.agenda.resource.bean;

import javax.ws.rs.QueryParam;

/**
 * Created by silvanei on 30/07/16.
 */
public class PaginatedFilterBean {

    protected @QueryParam("offset") int offset;
    protected @QueryParam("limit") int limit;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {

        if(limit == 0) {
            limit = 5;
        }

        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
