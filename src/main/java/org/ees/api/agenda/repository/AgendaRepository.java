package org.ees.api.agenda.repository;

import org.ees.api.agenda.entity.Event;
import org.ees.api.agenda.entity.ReservaCliente;
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

    public Event findEvent(Integer salaoId, DateTime dia, Integer eventId);

    public Event findById(Integer salaoId, Integer agendaId);

    public Integer add(Integer salaoId, Event event);

    public Integer update(Integer salaoId, Integer agendaId, Event event);

    public List<ReservaCliente> findByClientId(Integer clienteId);

    public Integer cancelarReservaCliente(Integer clienteId, Integer reservaId);
}
