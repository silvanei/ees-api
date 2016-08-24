package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.Servico;
import org.ees.api.agenda.infra.db.CollectionPaginated;
import org.ees.api.agenda.infra.exceptions.DataNotFoundException;
import org.ees.api.agenda.repository.ServicoRepository;
import org.ees.api.agenda.service.ServicoService;

import javax.inject.Inject;
import java.util.List;


public class ServicoServiceImpl implements ServicoService {

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
	public Servico update(Integer salaoId, Integer servicoId, Servico servico) {

		if(null == servicoRepository.findById(salaoId, servicoId)) {
			throw new DataNotFoundException("Serviço não encontrado");
		}
		servico.setId(servicoId);

		servicoRepository.update(servico);
		
		return findById(salaoId, servicoId);
	}

	@Override
	public Integer delete(Integer salaoId, Integer servicoId) {
        if(null == servicoRepository.findById(salaoId, servicoId)) {
            throw new DataNotFoundException("Serviço não encontrado");
        }

        return servicoRepository.delete(servicoId);
	}

	@Override
	public Servico findById(Integer salaoId, Integer idServico) {
		Servico servico = servicoRepository.findById(salaoId, idServico);

		if(null == servico) {
			throw new DataNotFoundException("Não foi possivel encontrar o serviço.");
		}

		return servico;
	}

	@Override
	public CollectionPaginated<Servico> findByIdSalao(Integer idSalao, int limit, int offset) {
		return servicoRepository.findByIdSalao(idSalao, limit, offset);
	}

	@Override
	public CollectionPaginated<Servico> findByIdSalao(Integer idSalao) {
		return servicoRepository.findByIdSalao(idSalao);
	}

	@Override
	public List<Servico> findByIdFuncionario(Integer salaoId, Integer funcionarioId) {
		return servicoRepository.findByIdFuncionario(salaoId, funcionarioId);
	}

	@Override
	public List<Servico> findNotInFuncionario(Integer salaoId, Integer funcionarioId) {
        return servicoRepository.findNotInFuncionario(salaoId, funcionarioId);
	}
}
