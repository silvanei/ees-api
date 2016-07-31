package org.ees.api.agenda.infra.db;

/**
 * Created by silvanei on 30/07/16.
 */

public class Metadata {

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
}
