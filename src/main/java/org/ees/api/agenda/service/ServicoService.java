package org.ees.api.agenda.service;

import org.ees.api.agenda.entity.Servico;
import org.ees.api.agenda.infra.db.CollectionPaginated;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ServicoService {
	
	public Servico insert(Integer idSalao, Servico servico);

	public Servico update(Integer idSalao, Servico servico);
	
	public Integer delete(Integer idSalao, Servico servico);

	public Servico findById(Integer idServico);
	
	public CollectionPaginated<Servico> findByIdSalao(Integer idSalao, int limit, int offset);

}
