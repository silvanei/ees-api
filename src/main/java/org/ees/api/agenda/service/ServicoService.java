package org.ees.api.agenda.service;

import java.util.List;

import org.ees.api.agenda.entity.Servico;

public interface ServicoService {
	
	public Servico insert(Integer idSalao, Servico servico);

	public Servico update(Integer idSalao, Servico servico);
	
	public Integer delete(Integer idSalao, Servico servico);

	public Servico findById(Integer idServico);
	
	public List<Servico> findByIdSalao(Integer idSalao, int limit, int offset);

}
