package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.*;
import org.ees.api.agenda.infra.auth.Digest;
import org.ees.api.agenda.infra.db.CollectionPaginated;
import org.ees.api.agenda.infra.exceptions.ConflictException;
import org.ees.api.agenda.infra.exceptions.DataNotFoundException;
import org.ees.api.agenda.repository.FuncionarioRepository;
import org.ees.api.agenda.service.AcessoService;
import org.ees.api.agenda.service.FuncionarioService;
import org.ees.api.agenda.service.HorarioTrabalhoService;
import org.ees.api.agenda.service.ServicoService;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by silvanei on 12/08/2016.
 */
public class FuncionarioServiceImpl implements FuncionarioService {

    private FuncionarioRepository funcionarioRepository;
    private ServicoService servicoService;
    private HorarioTrabalhoService horarioTrabalhoService;
    private AcessoService acessoService;

    @Inject
    public FuncionarioServiceImpl(
            FuncionarioRepository funcionarioRepository,
            ServicoService servicoService,
            HorarioTrabalhoService horarioTrabalhoService,
            AcessoService acessoService
    ) {
        this.funcionarioRepository = funcionarioRepository;
        this.servicoService = servicoService;
        this.horarioTrabalhoService = horarioTrabalhoService;
        this.acessoService = acessoService;
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
        CollectionPaginated<Funcionario> funcionarios;
        if(limit > 0) {
            funcionarios = funcionarioRepository.findByIdSalao(salaoId, limit, offset);
        } else {
            funcionarios = funcionarioRepository.findByIdSalao(salaoId);
        }

        for(Funcionario funcionario: funcionarios.getItems()) {
            funcionario.setServicosPrestados(servicoService.findByIdFuncionario(salaoId, funcionario.getId()));
            funcionario.setServicosDisponiveis(servicoService.findNotInFuncionario(salaoId, funcionario.getId()));
            funcionario.setHorariosTrabalho(horarioTrabalhoService.findByIdFuncionario(funcionario.getId()));
        }

        return funcionarios;
    }

    @Override
    public CollectionPaginated<Funcionario> findByIdSalao(Integer salaoId) {
        return findByIdSalao(salaoId, 0, 0);
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

    @Override
    public Acesso addAcesso(Integer salaoId, Integer funcionarioId, Acesso acesso) {

        try {
            acesso.setSenha(Digest.generate(acesso.getSenha()));
        } catch (NoSuchAlgorithmException e) {
            throw new BadRequestException("Não foi possível criar a senha");
        } catch (UnsupportedEncodingException e) {
            throw new BadRequestException("Não foi possível criar a senha");
        }

        Funcionario funcionario = funcionarioRepository.findById(salaoId, funcionarioId);
        if(null == funcionario) {
            throw new DataNotFoundException("Funcionário não encontrado");
        }

        if(funcionarioRepository.hasAcesso(salaoId, funcionarioId)) {
            throw new ConflictException("Funcionário já possui um acesso");
        }


        Integer id = acessoService.insert(acesso);
        funcionarioRepository.addAcesso(salaoId, funcionarioId, id);

        return acessoService.findById(id);
    }

    @Override
    public Integer removeAcesso(Integer salaoId, Integer funcionarioId, Acesso acesso) {
        return acessoService.removeAcesso(salaoId, funcionarioId, acesso);
    }
}
