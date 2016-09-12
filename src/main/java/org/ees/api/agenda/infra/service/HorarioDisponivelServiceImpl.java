package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.Funcionario;
import org.ees.api.agenda.entity.HorarioDisponivel;
import org.ees.api.agenda.entity.Salao;
import org.ees.api.agenda.entity.Servico;
import org.ees.api.agenda.service.DadosSalaoService;
import org.ees.api.agenda.service.FuncionarioService;
import org.ees.api.agenda.service.HorarioDisponivelService;
import org.ees.api.agenda.service.ServicoService;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Minutes;
import org.joda.time.PeriodType;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by silvanei on 12/09/16.
 */
public class HorarioDisponivelServiceImpl implements HorarioDisponivelService {

    private FuncionarioService funcionarioService;
    private ServicoService servicoService;


    @Inject
    public HorarioDisponivelServiceImpl(FuncionarioService funcionarioService, ServicoService servicoService) {
        this.funcionarioService = funcionarioService;
        this.servicoService = servicoService;
    }

    @Override
    public List<HorarioDisponivel> findBy(Integer salaoId, Integer servicoId, Integer funcionarioId) {
        List<HorarioDisponivel> horariosDisponiveis = new ArrayList<>();

        Servico servico = servicoService.findById(salaoId, servicoId);
        int duracaoServico = servico.getDuracao();

        Funcionario funcionario = funcionarioService.findById(salaoId, funcionarioId);
        DateTime dataInicio = new DateTime(funcionario.getHorariosTrabalho().get(1).getEntrada1().getTime());
        DateTime dataFinal = new DateTime(funcionario.getHorariosTrabalho().get(1).getSaida1().getTime());



        // Antes do intervalo
        for(DateTime data = dataInicio; data.isBefore(dataFinal); data = data.plusMinutes(duracaoServico)) {
            horariosDisponiveis.add(new HorarioDisponivel(data));
        }

        dataInicio = new DateTime(funcionario.getHorariosTrabalho().get(1).getEntrada2().getTime());
        dataFinal = new DateTime(funcionario.getHorariosTrabalho().get(1).getSaida2().getTime());


        // depois do intervalo
        for(DateTime data = dataInicio; data.isBefore(dataFinal); data = data.plusMinutes(duracaoServico)) {
            horariosDisponiveis.add(new HorarioDisponivel(data));
        }

        return horariosDisponiveis;
    }
}
