package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.Funcionario;
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
    public Integer insert(Funcionario funcionario, Integer idSalao, Integer idAcesso) {
        return funcionarioRepository.insert(funcionario, idSalao, idAcesso);
    }

    @Override
    public Funcionario findById(Integer idFuncionario) {
        return funcionarioRepository.findById(idFuncionario);
    }
}
