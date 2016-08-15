package org.ees.api.agenda.repository;

import org.ees.api.agenda.entity.Funcionario;
import org.ees.api.agenda.infra.db.CollectionPaginated;

public interface FuncionarioRepository {

    public Integer insert(Integer idSalao, Funcionario funcionario);

    public Funcionario findById(Integer idSalao, Integer funcionarioId);

    public CollectionPaginated<Funcionario> findByIdSalao(Integer salaoId, int limit, int offset);

    public Integer update(Integer salaoId, Integer funcionarioId, Funcionario funcionario);

    public Integer delete(Integer salaoId, Integer funcionarioId);

    public Integer addServico(Integer salaoId, Integer funcionarioId, Integer servicoId);
}
