package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.DiaDaSemana;
import org.ees.api.agenda.entity.Funcionario;
import org.ees.api.agenda.entity.HorarioTrabalho;
import org.ees.api.agenda.entity.Servico;
import org.ees.api.agenda.infra.db.CollectionPaginated;
import org.ees.api.agenda.infra.exceptions.DataNotFoundException;
import org.ees.api.agenda.repository.FuncionarioRepository;
import org.ees.api.agenda.service.FuncionarioService;
import org.ees.api.agenda.service.HorarioTrabalhoService;
import org.ees.api.agenda.service.ServicoService;

import javax.inject.Inject;

/**
 * Created by silvanei on 12/08/2016.
 */
public class FuncionarioServiceImpl implements FuncionarioService {

    private FuncionarioRepository funcionarioRepository;
    private ServicoService servicoService;
    private HorarioTrabalhoService horarioTrabalhoService;

    @Inject
    public FuncionarioServiceImpl(
            FuncionarioRepository funcionarioRepository,
            ServicoService servicoService,
            HorarioTrabalhoService horarioTrabalhoService
    ) {
        this.funcionarioRepository = funcionarioRepository;
        this.servicoService = servicoService;
        this.horarioTrabalhoService = horarioTrabalhoService;
    }

    @Override
    public Funcionario insert(Integer idSalao, Funcionario funcionario) {
        int idFuncionario = funcionarioRepository.insert(idSalao, funcionario);

        return findById(idSalao, idFuncionario);
    }

    @Override
    public Funcionario findById(Integer idSalao, Integer idFuncionario) {
        Funcionario funcionario = funcionarioRepository.findById(idSalao, idFuncionario);

        if (null == funcionario) {
            throw new DataNotFoundException("Funcionario não encontrado");
        }

        funcionario.setServicosPrestados(servicoService.findByIdFuncionario(idSalao, idFuncionario));
        funcionario.setServicosDisponiveis(servicoService.findNotInFuncionario(idSalao, funcionario.getId()));
        funcionario.setHorariosTrabalho(horarioTrabalhoService.findByIdFuncionario(funcionario.getId()));

        return funcionario;
    }

    @Override
    public CollectionPaginated<Funcionario> findByIdSalao(Integer salaoId, int limit, int offset) {
        CollectionPaginated<Funcionario> funcionarios = funcionarioRepository.findByIdSalao(salaoId, limit, offset);
        for(Funcionario funcionario: funcionarios.getItems()) {
            funcionario.setServicosPrestados(servicoService.findByIdFuncionario(salaoId, funcionario.getId()));
            funcionario.setServicosDisponiveis(servicoService.findNotInFuncionario(salaoId, funcionario.getId()));
            funcionario.setHorariosTrabalho(horarioTrabalhoService.findByIdFuncionario(funcionario.getId()));
        }

        return funcionarios;
    }

    @Override
    public Funcionario update(Integer salaoId, Integer funcionarioId, Funcionario funcionario) {

        findById(salaoId, funcionarioId);

        funcionarioRepository.update(salaoId, funcionarioId, funcionario);

        return findById(salaoId, funcionarioId);
    }

    @Override
    public Integer delete(Integer salaoId, Integer funcionarioId) {
        findById(salaoId, funcionarioId);

        return funcionarioRepository.delete(salaoId, funcionarioId);
    }

    @Override
    public Funcionario addServico(Integer salaoId, Integer funcionarioId, Integer servicoId) {

        Servico servico = servicoService.findById(salaoId, servicoId);
        if(null == servico) {
            throw new DataNotFoundException("Servico não encontrado");
        }

        findById(salaoId, funcionarioId);

        funcionarioRepository.addServico(salaoId, funcionarioId, servicoId);

        return findById(salaoId, funcionarioId);
    }

    @Override
    public Integer removeServico(Integer salaoId, Integer funcionarioId, Integer servicoId) {
        Servico servico = servicoService.findById(salaoId, servicoId);
        if(null == servico) {
            throw new DataNotFoundException("Servico não encontrado");
        }

        findById(salaoId, funcionarioId);

        return funcionarioRepository.removeServico(salaoId, funcionarioId, servicoId);
    }

    @Override
    public HorarioTrabalho addHorario(Integer salaoId, Integer funcionarioId, DiaDaSemana diaDaSemana, HorarioTrabalho horario) {
        findById(salaoId, funcionarioId);

        DiaDaSemana dia = horarioTrabalhoService.add(salaoId, funcionarioId, diaDaSemana, horario);

        return horarioTrabalhoService.findByDiaSemana(salaoId, funcionarioId, dia);
    }

    @Override
    public HorarioTrabalho updateHorario(Integer salaoId, Integer funcionarioId, DiaDaSemana diaDaSemana, HorarioTrabalho horario) {
        findById(salaoId, funcionarioId);

        HorarioTrabalho horarioTrabalho = horarioTrabalhoService.findByDiaSemana(salaoId, funcionarioId, diaDaSemana);
        if(null == horarioTrabalho) {
            throw new DataNotFoundException("Horario de trabalho não encontrado para atualização");
        }

        horarioTrabalhoService.update(salaoId, funcionarioId, diaDaSemana, horario);

        return horarioTrabalhoService.findByDiaSemana(salaoId, funcionarioId, diaDaSemana);
    }

    @Override
    public DiaDaSemana deleteHorario(Integer salaoId, Integer funcionarioId, DiaDaSemana diaDaSemana) {
        HorarioTrabalho horarioTrabalho = horarioTrabalhoService.findByDiaSemana(salaoId, funcionarioId, diaDaSemana);
        if(null == horarioTrabalho) {
            throw new DataNotFoundException("Horario de trabalho não encontrado para atualização");
        }

        findById(salaoId, funcionarioId);

        return horarioTrabalhoService.deleteHorario(salaoId, funcionarioId, diaDaSemana);
    }
}
