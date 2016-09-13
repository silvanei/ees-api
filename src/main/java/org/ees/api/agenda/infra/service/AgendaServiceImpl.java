package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.Calendar;
import org.ees.api.agenda.entity.Event;
import org.ees.api.agenda.entity.Resource;
import org.ees.api.agenda.repository.AgendaRepository;
import org.ees.api.agenda.service.AgendaService;
import org.joda.time.DateTime;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by silvanei on 10/09/16.
 */
public class AgendaServiceImpl implements AgendaService {

    @Inject
    private AgendaRepository agendaRepository;

    @Override
    public Calendar getDay(Integer salaoId, DateTime start, DateTime end) {

        Calendar calendar = new Calendar();
        List<Resource> resources = agendaRepository.findResource(salaoId, start, end);

        calendar.setResources(resources);

        List<Event> events = agendaRepository.findEvents(salaoId, start, end);
        calendar.setEvents(events);

        return calendar;
    }

    @Override
    public Calendar getDay(Integer salaoId, Integer funcionarioId, DateTime start, DateTime end) {
        Calendar calendar = new Calendar();
        List<Resource> resources = agendaRepository.findResourceByFuncionarioId(salaoId, funcionarioId, start, end);

        calendar.setResources(resources);

        List<Event> events = agendaRepository.findEventsByFuncionarioId(salaoId, funcionarioId, start, end);
        calendar.setEvents(events);

        return calendar;
    }
}
