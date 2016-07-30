package org.ees.api.agenda.repository;

import java.util.List;

import org.ees.api.agenda.entity.Servico;

public interface ServicoRepository {
	
	public Integer insert(Integer idSalao, Servico servico);

	public Integer update(Servico servico);
	
	public Integer delete(Servico servico);

	public Servico findById(Integer idServico);
	
	public List<Servico> findByIdSalao(Integer idSalao, int limit, int offset);
}
