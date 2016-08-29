package org.ees.api.agenda.repository;

import org.ees.api.agenda.entity.Funcionario;
import org.ees.api.agenda.entity.HorarioTrabalho;
import org.ees.api.agenda.infra.db.CollectionPaginated;

import java.util.List;

public interface FuncionarioRepository {

    public Integer insert(Integer idSalao, Funcionario funcionario);
    public Integer insert(Integer idSalao, Funcionario funcionario, Integer acessoId);

    public Funcionario findById(Integer idSalao, Integer funcionarioId);

    public CollectionPaginated<Funcionario> findByIdSalao(Integer salaoId, int limit, int offset);

    public Integer update(Integer salaoId, Integer funcionarioId, Funcionario funcionario);

    public Integer delete(Integer salaoId, Integer funcionarioId);

    public Integer addServico(Integer salaoId, Integer funcionarioId, Integer servicoId);

    public Integer removeServico(Integer salaoId, Integer funcionarioId, Integer servicoId);

    public Integer addAcesso(Integer salaoId, Integer funcionarioId, Integer acessoId);

    public Boolean hasAcesso(Integer salaoId, Integer funcionarioId);

    public CollectionPaginated<Funcionario> findByIdSalao(Integer salaoId);
}
