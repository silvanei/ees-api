package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.*;
import org.ees.api.agenda.infra.exceptions.ConflictException;
import org.ees.api.agenda.infra.exceptions.DataNotFoundException;
import org.ees.api.agenda.repository.AgendaRepository;
import org.ees.api.agenda.resource.bean.Agendamento;
import org.ees.api.agenda.service.*;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import javax.inject.Inject;
import java.sql.Time;
import java.util.List;

/**
 * Created by silvanei on 10/09/16.
 */
public class AgendaServiceImpl implements AgendaService {

    @Inject
    private AgendaRepository agendaRepository;

    @Inject
    private DadosSalaoService dadosSalaoService;

    @Inject
    private ServicoService servicoService;

    @Inject
    private HorarioTrabalhoService horarioTrabalhoService;

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
    public Event getDay(Integer salaoId, DateTime dia, Integer eventId) {
        Event event = agendaRepository.findEvent(salaoId, dia, eventId);

        if (null == event) {
            throw new DataNotFoundException("Agendamento não encontrado");
        }

        return event;
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

    @Override
    public Integer add(Integer salaoId, Integer clienteId, Integer servicoId, Integer funcionarioId, DateTime date, Time hora, String observacao) {

        DateTime dateTime = date.withHourOfDay(hora.getHours());
        dateTime = dateTime.withMinuteOfHour(hora.getMinutes());

        if(dateTime.isBefore(new DateTime())) {
            throw new ConflictException("Não é possível agendar para uma data anterior a data atual");
        }

        Salao salao = dadosSalaoService.findById(salaoId);
        LocalTime horarioInicio = new DateTime(salao.getHorarioDeFuncionamento().getHorarioInicio().getTime()).toLocalTime();
        LocalTime horarioFinal = new DateTime(salao.getHorarioDeFuncionamento().getHorarioFinal().getTime()).toLocalTime();

        Servico servico = servicoService.findById(salaoId, servicoId);
        int duracaoServico = servico.getDuracao();
        int diaDaSemana = dateTime.getDayOfWeek();
        if (diaDaSemana == 7) {
            diaDaSemana = 0;
        }
        DateTime proximo = dateTime.plusMinutes(duracaoServico);

        if (dateTime.toLocalTime().isBefore(horarioInicio)) {
            throw new ConflictException("Não é possível agendar para um horário anterior ao horario de abertura do estabelecimento");
        }

        if (dateTime.toLocalTime().isAfter(horarioFinal.minusMinutes(duracaoServico))) {
            throw new ConflictException("Não é possível agendar para um horário posterior ao horario de fechamento do estabelecimento");
        }


        try {
            HorarioTrabalho horarioTrabalho = horarioTrabalhoService.findByDiaSemana(salaoId, funcionarioId, new DiaDaSemana(diaDaSemana));
            if (null == horarioTrabalho) {
                throw new DataNotFoundException("Profissional não trabalha neste dia.");
            }
            LocalTime entrada = new DateTime(horarioTrabalho.getEntrada1().getTime()).toLocalTime();
            LocalTime saidaIntervalo = new DateTime(horarioTrabalho.getSaida1().getTime()).toLocalTime();
            LocalTime entradaIntervalo = new DateTime(horarioTrabalho.getEntrada2().getTime()).toLocalTime();
            LocalTime saida = new DateTime(horarioTrabalho.getSaida2().getTime()).toLocalTime();

            if (dateTime.toLocalTime().isBefore(entrada)) {
                throw new ConflictException("Não é possível agendar para um horário anterior ao horario de trabalho do colaborador");
            }

            if (dateTime.toLocalTime().isAfter(saida)) {
                throw new ConflictException("Não é possível agendar para um horário posterior ao horario de trabalho do colaborador");
            }

            if(validaConflito(dateTime.toLocalTime(), proximo.toLocalTime(), saidaIntervalo, entradaIntervalo)) {
                throw new ConflictException("Não é possível agendar no horario de intervalo do colaborador");
            }

        } catch (Exception e) {
            throw new ConflictException(e.getMessage());
        }

        Calendar calendar = getDay(salaoId, funcionarioId, dateTime, dateTime);
        // Agendamentos do dia
        for (Event event : calendar.getEvents()) {
            if(validaConflito(dateTime.toLocalTime(), proximo.toLocalTime(), event.getStart().toLocalTime(), event.getEnd().toLocalTime())) {
                throw new ConflictException("Horario em conflito com outro agendamento");
            }
        }


        return agendaRepository.add(
                salaoId,
                clienteId,
                servicoId,
                funcionarioId,
                dateTime,
                observacao
        );
    }

    private boolean validaConflito(LocalTime data, LocalTime proximo, LocalTime inicio, LocalTime fim) {

        if(data.isAfter(inicio) || data.isEqual(inicio) ) {
            if(data.isBefore(fim) ) {
                return true;
            }
        } else {
            if(proximo.isAfter(fim)) {
                return true;
            }
        }

        if(proximo.isAfter(inicio) ) {
            if(proximo.isBefore(fim) || proximo.isEqual(fim) ) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Event update(Integer salaoId, Integer eventId, DateTime date, Agendamento agendamento) {
        getDay(salaoId, date, eventId);


        DateTime dateTime = agendamento.getData();

        if(dateTime.isBefore(new DateTime())) {
            throw new ConflictException("Não é possível agendar para uma data anterior a data atual");
        }

        Salao salao = dadosSalaoService.findById(salaoId);
        LocalTime horarioInicio = new DateTime(salao.getHorarioDeFuncionamento().getHorarioInicio().getTime()).toLocalTime();
        LocalTime horarioFinal = new DateTime(salao.getHorarioDeFuncionamento().getHorarioFinal().getTime()).toLocalTime();

        Servico servico = servicoService.findById(salaoId, agendamento.getServicoId());
        int duracaoServico = servico.getDuracao();
        int diaDaSemana = dateTime.getDayOfWeek();
        if (diaDaSemana == 7) {
            diaDaSemana = 0;
        }
        DateTime proximo = dateTime.plusMinutes(duracaoServico);

        if (dateTime.toLocalTime().isBefore(horarioInicio)) {
            throw new ConflictException("Não é possível agendar para um horário anterior ao horario de abertura do estabelecimento");
        }

        if (dateTime.toLocalTime().isAfter(horarioFinal.minusMinutes(duracaoServico))) {
            throw new ConflictException("Não é possível agendar para um horário posterior ao horario de fechamento do estabelecimento");
        }


        try {
            HorarioTrabalho horarioTrabalho = horarioTrabalhoService.findByDiaSemana(salaoId, agendamento.getFuncionarioId(), new DiaDaSemana(diaDaSemana));
            if (null == horarioTrabalho) {
                throw new DataNotFoundException("Profissional não trabalha neste dia.");
            }
            LocalTime entrada = new DateTime(horarioTrabalho.getEntrada1().getTime()).toLocalTime();
            LocalTime saidaIntervalo = new DateTime(horarioTrabalho.getSaida1().getTime()).toLocalTime();
            LocalTime entradaIntervalo = new DateTime(horarioTrabalho.getEntrada2().getTime()).toLocalTime();
            LocalTime saida = new DateTime(horarioTrabalho.getSaida2().getTime()).toLocalTime();

            if (dateTime.toLocalTime().isBefore(entrada)) {
                throw new ConflictException("Não é possível agendar para um horário anterior ao horario de trabalho do colaborador");
            }

            if (dateTime.toLocalTime().isAfter(saida)) {
                throw new ConflictException("Não é possível agendar para um horário posterior ao horario de trabalho do colaborador");
            }

            if(validaConflito(dateTime.toLocalTime(), proximo.toLocalTime(), saidaIntervalo, entradaIntervalo)) {
                throw new ConflictException("Não é possível agendar no horario de intervalo do colaborador");
            }

        } catch (Exception e) {
            throw new ConflictException(e.getMessage());
        }

        Calendar calendar = getDay(salaoId, agendamento.getFuncionarioId(), dateTime, dateTime);
        // Agendamentos do dia
        for (Event event : calendar.getEvents()) {
            if(validaConflito(dateTime.toLocalTime(), proximo.toLocalTime(), event.getStart().toLocalTime(), event.getEnd().toLocalTime())) {
                if (! event.getId().equals(eventId)) {
                    throw new ConflictException("Horario em conflito com outro agendamento");
                }
            }
        }


        Integer eventUpdatesId = agendaRepository.update(salaoId, eventId, date, agendamento);

        return getDay(salaoId, agendamento.getData(), eventUpdatesId);
    }
}
