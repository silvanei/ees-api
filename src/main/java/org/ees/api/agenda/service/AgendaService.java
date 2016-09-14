package org.ees.api.agenda.service;

import org.ees.api.agenda.entity.Calendar;
import org.ees.api.agenda.entity.Event;
import org.ees.api.agenda.resource.bean.Agendamento;
import org.joda.time.DateTime;

import javax.inject.Inject;
import java.sql.Time;

/**
 * Created by silvanei on 10/09/16.
 */
public interface AgendaService {

    public Calendar getDay(Integer salaoId, DateTime start, DateTime end);

    public Event getDay(Integer salaoId, DateTime dia, Integer eventId);

    public Calendar getDay(Integer salaoId, Integer funcionarioId, DateTime start, DateTime end);

    public Integer add(Integer salaoId, Integer clienteId, Integer servicoId, Integer funcionarioId, DateTime date, Time hora, String observacao);

    public Event update(Integer salaoId, Integer eventId, DateTime date, Agendamento agendamento);
}
