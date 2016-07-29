package org.ees.api.agenda.infra.service;

import java.util.List;

import org.ees.api.agenda.entity.Servico;
import org.ees.api.agenda.infra.repository.ServicoRepositoryImpl;
import org.ees.api.agenda.repository.ServicoRepository;
import org.ees.api.agenda.service.ServicoService;

public class ServicoServiceImpl implements ServicoService {
	
	private ServicoRepository servicoRepository = new ServicoRepositoryImpl();

	@Override
	public Servico insert(Integer idSalao, Servico servico) {
		
		Integer idServico = servicoRepository.insert(idSalao, servico);
		
		return findById(idServico);
	}

	@Override
	public Servico update(Integer idSalao, Servico servico) {
		Integer idServico = servicoRepository.update(servico);
		
		return findById(idServico);
	}

	@Override
	public Integer delete(Integer idSalao, Servico servico) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Servico findById(Integer idServico) {
		return servicoRepository.findById(idServico);
	}

	@Override
	public List<Servico> findByIdSalao(Integer idSalao) {
		return servicoRepository.findByIdSalao(idSalao);
	}

}
