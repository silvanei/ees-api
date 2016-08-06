package org.ees.api.agenda.infra.db;

import java.util.List;

/**
 * Created by silvanei on 30/07/16.
 */

public class CollectionPaginated<T> {

    private List<T> items;

    private int limit;

    private int offset;

    private int count;

    public CollectionPaginated(List<T> items, int limit, int offset, int count) {
        this.items = items;
        this.limit = limit;
        this.offset = offset;
        this.count = count;
    }

    public List<T> getItems() {
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
