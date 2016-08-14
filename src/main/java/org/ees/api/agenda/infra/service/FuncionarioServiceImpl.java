package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.Funcionario;
import org.ees.api.agenda.infra.db.CollectionPaginated;
import org.ees.api.agenda.infra.exceptions.DataNotFoundException;
import org.ees.api.agenda.repository.FuncionarioRepository;
import org.ees.api.agenda.service.FuncionarioService;

import javax.inject.Inject;

/**
 * Created by silvanei on 12/08/2016.
 */
public class FuncionarioServiceImpl implements FuncionarioService {

    private FuncionarioRepository funcionarioRepository;

    @Inject
    public FuncionarioServiceImpl(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
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

        return funcionario;
    }

    @Override
    public CollectionPaginated<Funcionario> findByIdSalao(Integer salaoId, int limit, int offset) {
        return funcionarioRepository.findByIdSalao(salaoId, limit, offset);
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
}
