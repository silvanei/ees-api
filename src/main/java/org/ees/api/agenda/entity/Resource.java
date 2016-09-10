package org.ees.api.agenda.entity;

/**
 * Created by silvanei on 10/09/16.
 */
public class Resource {

    private Integer id;
    private String title;

    public Resource() {}

    public Resource(Integer id, String title) {
        setId(id);
        setTitle(title);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
