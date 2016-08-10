package org.ees.api.agenda.service;

import org.ees.api.agenda.entity.Servico;
import org.ees.api.agenda.infra.db.CollectionPaginated;

public interface ServicoService {
	
	public Servico insert(Integer salaoId, Servico servico);

	public Servico update(Integer salaoId, Integer servicoId, Servico servico);
	
	public Integer delete(Integer salaoId, Integer servicoId);

	public Servico findById(Integer salaoId, Integer idServico);
	
	public CollectionPaginated<Servico> findByIdSalao(Integer salaoId, int limit, int offset);

}
