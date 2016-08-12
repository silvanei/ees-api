package org.ees.api.agenda.service;

import org.ees.api.agenda.entity.Funcionario;

/**
 * Created by silvanei on 12/08/2016.
 */
public interface FuncionarioService {

    public Integer insert(Funcionario funcionario, Integer idSalao, Integer idAcesso);

    public Funcionario findById(Integer idFuncionario);
}
