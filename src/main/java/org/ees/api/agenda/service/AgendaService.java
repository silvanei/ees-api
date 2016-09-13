package org.ees.api.agenda.service;

import org.ees.api.agenda.entity.Calendar;
import org.joda.time.DateTime;

import javax.inject.Inject;

/**
 * Created by silvanei on 10/09/16.
 */
public interface AgendaService {

    public Calendar getDay(Integer salaoId, DateTime start, DateTime end);

    public Calendar getDay(Integer salaoId, Integer funcionarioId, DateTime start, DateTime end);
}
