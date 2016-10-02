package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.*;
import org.ees.api.agenda.infra.exceptions.DataNotFoundException;
import org.ees.api.agenda.service.*;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by silvanei on 12/09/16.
 */
public class HorarioDisponivelServiceImpl implements HorarioDisponivelService {

    private ServicoService servicoService;
    private AgendaService agendaService;
    private HorarioTrabalhoService horarioTrabalhoService;
    private HorarioDeFuncionamentoService horarioDeFuncionamentoService;


    @Inject
    public HorarioDisponivelServiceImpl(
            ServicoService servicoService,
            AgendaService agendaService,
            HorarioTrabalhoService horarioTrabalhoService,
            HorarioDeFuncionamentoService horarioDeFuncionamentoService
    ) {
        this.servicoService = servicoService;
        this.agendaService = agendaService;
        this.horarioTrabalhoService = horarioTrabalhoService;
        this.horarioDeFuncionamentoService = horarioDeFuncionamentoService;
    }

    @Override
    public HorarioDisponivel findBy(Integer salaoId, Integer servicoId, Integer funcionarioId, DateTime dia) {
        HorarioDisponivel horarioDisponivel = new HorarioDisponivel(dia);

        Servico servico = servicoService.findById(salaoId, servicoId);
        int duracaoServico = servico.getDuracao();
        int diaDaSemana = dia.getDayOfWeek();
        if (diaDaSemana == 7) {
            diaDaSemana = 0;
        }

        try {

            if (! validaDiasDeTrabalhoSalao(salaoId, diaDaSemana)) {
                return horarioDisponivel;
            }

            HorarioTrabalho horarioTrabalho = horarioTrabalhoService.findByDiaSemana(salaoId, funcionarioId, new DiaDaSemana(diaDaSemana));

            if (null == horarioTrabalho) {
                return horarioDisponivel;
            }

            LocalTime entrada = new DateTime(horarioTrabalho.getEntrada1().getTime()).toLocalTime();
            LocalTime saidaIntervalo = new DateTime(horarioTrabalho.getSaida1().getTime()).toLocalTime();
            LocalTime entradaIntervalo = new DateTime(horarioTrabalho.getEntrada2().getTime()).toLocalTime();
            LocalTime saida = new DateTime(horarioTrabalho.getSaida2().getTime()).toLocalTime();


            Calendar calendar = agendaService.getDay(salaoId, funcionarioId, dia,  dia);


            for(LocalTime data = entrada; data.isBefore(saida); data = data.plusMinutes(duracaoServico)) {
                LocalTime proximo = data.plusMinutes(duracaoServico);

                // Horario do intervalo
                if(validaConflito(data, proximo, saidaIntervalo, entradaIntervalo)) {
                    data = entradaIntervalo;
                }

                // Agendamentos do dia
                for (Event event : calendar.getEvents()) {
                    if(validaConflito(data, proximo, event.getStart().toLocalTime(), event.getEnd().toLocalTime())) {
                        data = event.getEnd().toLocalTime();
                        proximo = data.plusMinutes(duracaoServico);
                    }
                }


                if (dia.isAfterNow()) {
                    horarioDisponivel.getHorarios().add(data.toDateTimeToday());
                } else {
                    if (data.isAfter(new LocalTime())) {
                        horarioDisponivel.getHorarios().add(data.toDateTimeToday());
                    }
                }


            }

        } catch (Exception e) {
            throw new DataNotFoundException(e.getMessage());
        }

        return horarioDisponivel;
    }

    @Override
    public List<HorarioDisponivel> findBy(Integer salaoId, Integer servicoId, Integer funcionarioId) {
        List<HorarioDisponivel> horarioDisponivels = new ArrayList<>();

        for (int i = 0; i <= 6; i++) {
            horarioDisponivels.add(findBy(salaoId, servicoId, funcionarioId, new DateTime().plusDays(i)));
        }

        return horarioDisponivels;
    }

    private boolean validaDiasDeTrabalhoSalao(Integer salaoId, Integer diaDaSemana) {

        HorarioDeFuncionamento horarioDeFuncionamentoSalao = horarioDeFuncionamentoService.byIdSalao(salaoId);

        switch (diaDaSemana) {
            case DiaDaSemana.DOMINGO:
                return horarioDeFuncionamentoSalao.isDomingo();
            case DiaDaSemana.SEGUNDA:
                return horarioDeFuncionamentoSalao.isSegunda();
            case DiaDaSemana.TERCA:
                return horarioDeFuncionamentoSalao.isTerca();
            case DiaDaSemana.QUARTA:
                return horarioDeFuncionamentoSalao.isQuarta();
            case DiaDaSemana.QUINTA:
                return horarioDeFuncionamentoSalao.isQuinta();
            case DiaDaSemana.SEXTA:
                return horarioDeFuncionamentoSalao.isSexta();
            case DiaDaSemana.SABADO:
                return horarioDeFuncionamentoSalao.isSabado();
            default:
                return false;
        }
    }

    private boolean validaConflito(LocalTime data, LocalTime proximo, LocalTime inicio, LocalTime fim) {

        if(data.isAfter(inicio) || data.isEqual(inicio) ) {
            if(data.isBefore(fim) ) {
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
}
