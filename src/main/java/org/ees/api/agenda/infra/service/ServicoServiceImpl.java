package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.Servico;
import org.ees.api.agenda.infra.db.CollectionPaginated;
import org.ees.api.agenda.infra.exceptions.DataNotFoundException;
import org.ees.api.agenda.repository.ServicoRepository;
import org.ees.api.agenda.service.ServicoService;

import javax.inject.Inject;


public final class ServicoServiceImpl implements ServicoService {

	private ServicoRepository servicoRepository;

	@Inject
	public ServicoServiceImpl(ServicoRepository servicoRepository) {
		this.servicoRepository = servicoRepository;
	}

	@Override
	public Servico insert(Integer idSalao, Servico servico) {
		
		Integer idServico = servicoRepository.insert(idSalao, servico);
		
		return findById(idSalao, idServico);
	}

	@Override
	public Servico update(Integer idSalao, Servico servico) {
		Integer idServico = servicoRepository.update(servico);
		
		return findById(idSalao, idServico);
	}

	@Override
	public Integer delete(Integer idSalao, Servico servico) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Servico findById(Integer idSalao, Integer idServico) {
		Servico servico = servicoRepository.findById(idSalao, idServico);

		if(null == servico) {
			throw new DataNotFoundException("Não foi possivel encontrar o serviço.");
		}

		return servico;
	}

	@Override
	public CollectionPaginated<Servico> findByIdSalao(Integer idSalao, int limit, int offset) {
		return servicoRepository.findByIdSalao(idSalao, limit, offset);
	}

}
