package org.ees.api.agenda.repository;

import org.ees.api.agenda.entity.Event;
import org.ees.api.agenda.entity.Resource;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by silvanei on 10/09/16.
 */
public interface AgendaRepository {
    public List<Resource> findyResource(Integer salaoId, DateTime start, DateTime end);

    public List<Event> findyEvents(Integer salaoId, DateTime start, DateTime end);
}
