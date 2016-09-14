package org.ees.api.agenda.repository;

import org.ees.api.agenda.entity.Event;
import org.ees.api.agenda.entity.Resource;
import org.ees.api.agenda.resource.bean.Agendamento;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by silvanei on 10/09/16.
 */
public interface AgendaRepository {
    public List<Resource> findResource(Integer salaoId, DateTime start, DateTime end);

    public List<Event> findEvents(Integer salaoId, DateTime start, DateTime end);

    public List<Resource> findResourceByFuncionarioId(Integer salaoId, Integer funcionarioId, DateTime start, DateTime end);

    public List<Event> findEventsByFuncionarioId(Integer salaoId, Integer funcionarioId, DateTime start, DateTime end);

    public Integer add(Integer salaoId, Integer clienteId, Integer servicoId, Integer funcionarioId, DateTime dateTime, String observacao);

    public Event findEvent(Integer salaoId, DateTime dia, Integer eventId);

    public Integer update(Integer salaoId, Integer eventId, DateTime date, Agendamento agendamento);
}
