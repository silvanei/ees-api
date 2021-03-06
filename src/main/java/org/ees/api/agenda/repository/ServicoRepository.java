package org.ees.api.agenda.repository;

import org.ees.api.agenda.entity.Servico;
import org.ees.api.agenda.infra.db.CollectionPaginated;

import java.util.List;

public interface ServicoRepository {
	
	public Integer insert(Integer idSalao, Servico servico);

	public Integer update(Servico servico);
	
	public Integer delete(Integer servicoId);

	public Servico findById(Integer salaoId, Integer idServico);
	
	public CollectionPaginated<Servico> findByIdSalao(Integer idSalao, int limit, int offset);

	public CollectionPaginated<Servico> findByIdSalao(Integer idSalao);

	public List<Servico> findByIdFuncionario(Integer salaoId, Integer funcionarioId);

	public List<Servico> findNotInFuncionario(Integer salaoId, Integer funcionarioId);
}
