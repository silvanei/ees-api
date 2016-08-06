package org.ees.api.agenda.infra.db;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by silvanei on 30/07/16.
 */

public class CollectionPaginated<T> {

    private Metadata metadata;

    private List<T> result;

    public CollectionPaginated(Metadata metadata, List<T> result) {
        this.metadata = metadata;
        this.result = result;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public List<T> getResult() {
        return result;
    }
}
