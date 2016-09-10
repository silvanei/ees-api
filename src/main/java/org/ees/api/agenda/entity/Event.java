package org.ees.api.agenda.entity;

import org.joda.time.DateTime;

/**
 * Created by silvanei on 10/09/16.
 */
public class Event {

    private Integer resourceId;
    private String title;
    private DateTime start;
    private DateTime end;

    public Event() {
    }

    public Event(Integer resourceId, String title, DateTime start, DateTime end) {
        setResourceId(resourceId);
        setTitle(title);
        setStart(start);
        setEnd(end);
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DateTime getStart() {
        return start;
    }

    public void setStart(DateTime start) {
        this.start = start;
    }

    public DateTime getEnd() {
        return end;
    }

    public void setEnd(DateTime end) {
        this.end = end;
    }
}
