package org.ees.api.agenda.resource.collection;

import org.ees.api.agenda.infra.db.CollectionPaginated;

import java.util.List;

/**
 * Created by silvanei on 06/08/16.
 */
public abstract class CollectionResponse {

    private List items;

    private int limit;

    private int offset;

    private int count;

    public CollectionResponse(CollectionPaginated collectionPaginated) {
        this.items = collectionPaginated.getItems();
        this.limit = collectionPaginated.getLimit();
        this.offset = collectionPaginated.getOffset();
        this.count = collectionPaginated.getCount();
    }

    public List getItems() {
        return items;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getCount() {
        return count;
    }
}
