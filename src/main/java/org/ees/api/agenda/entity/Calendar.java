package org.ees.api.agenda.entity;

import java.util.List;

/**
 * Created by silvanei on 10/09/16.
 */
public class Calendar {

    private List<Resource> resources;
    private List<Event> events;

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
