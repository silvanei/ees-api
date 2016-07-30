package org.ees.api.agenda.infra.rest;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by silvanei on 30/07/16.
 */

@XmlRootElement
public class ResultSet {

    private Integer count;
    private Integer offset;
    private Integer limit;

    public ResultSet() {
    }

    public ResultSet(Integer count, Integer offset, Integer limit) {
        this.count = count;
        this.offset = offset;
        this.limit = limit;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
