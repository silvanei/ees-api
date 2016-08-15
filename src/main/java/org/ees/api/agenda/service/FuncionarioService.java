package org.ees.api.agenda.service;

import org.ees.api.agenda.entity.Funcionario;
import org.ees.api.agenda.infra.db.CollectionPaginated;

/**
 * Created by silvanei on 12/08/2016.
 */
public interface FuncionarioService {

    public Funcionario insert(Integer salaoId, Funcionario funcionario);

    public Funcionario findById(Integer salaoId, Integer funcionarioId);

    public CollectionPaginated<Funcionario> findByIdSalao(Integer salaoId, int limit, int offset);

    public Funcionario update(Integer salaoId, Integer funcionarioId, Funcionario sefuncionariorvico);

    public Integer delete(Integer salaoId, Integer funcionarioId);

    public Funcionario addServico(Integer salaoId, Integer funcionarioId, Integer servicoId);
}
