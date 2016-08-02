package org.ees.api.agenda.repository;

import org.ees.api.agenda.entity.Servico;
import org.ees.api.agenda.infra.db.CollectionPaginated;

public interface ServicoRepository {
	
	public Integer insert(Integer idSalao, Servico servico);

	public Integer update(Servico servico);
	
	public Integer delete(Servico servico);

	public Servico findById(Integer idServico);
	
	public CollectionPaginated<Servico> findByIdSalao(Integer idSalao, int limit, int offset);
}
